let streams = {};
let eventSource = null;

function connectSSE() {
    const sseUrl = '/coffee-break/sse';

  try {
    eventSource = new EventSource(sseUrl);

    eventSource.addEventListener('event', (event) => {
      const developerId = event.lastEventId || 'unknown';
      const message = event.data;
      handleMessage(developerId, message);
    });

    eventSource.onerror = (error) => {
      console.error('Erreur SSE:', error);

      if (eventSource.readyState === EventSource.CLOSED) {
        console.log('Connexion SSE fermée, tentative de reconnexion...');
        setTimeout(connectSSE, 5000);
      }
    };

    eventSource.onopen = () => {
      console.log('Connexion SSE établie');
    };
  } catch (error) {
    console.error('Erreur lors de la connexion SSE:', error);
  }
}

function handleMessage(id, message) {
  if (!streams[id]) {
    streams[id] = {
      messages: [],
      count: 0
    };
    createStreamCard(id);
  }

  streams[id].messages.unshift({
    content: message,
    timestamp: new Date()
  });
  streams[id].count++;

  updateStreamCard(id);
}

function createStreamCard(id) {
  const container = document.getElementById('streamsContainer');

  const card = document.createElement('div');
  card.className = 'stream-card';
  card.id = `stream-${id}`;
  card.innerHTML = `
    <div class="stream-header">
      <div class="stream-id">🧶 ${escapeHtml(id)}</div>
      <div class="stream-count" id="count-${id}">0</div>
    </div>
    <div class="stream-content" id="content-${id}"></div>
  `;

  container.appendChild(card);
}

function updateStreamCard(id) {
  const stream = streams[id];
  const countEl = document.getElementById(`count-${id}`);
  const contentEl = document.getElementById(`content-${id}`);

  if (countEl) {
    countEl.textContent = stream.count;
  }

  if (contentEl) {
    contentEl.innerHTML = stream.messages.slice(0, 20).map(msg => `
      <div class="message">
        <div class="message-time">${msg.timestamp.toLocaleTimeString()}</div>
        <div class="message-content">${escapeHtml(msg.content)}</div>
      </div>
    `).join('');
  }
}

function escapeHtml(text) {
  const div = document.createElement('div');
  div.textContent = text;
  return div.innerHTML;
}

async function handleSubmit(event) {
  event.preventDefault();

  const devName = document.getElementById('devName').value;
  const coffeeType = document.getElementById('coffeeType').value;
  const submitBtn = event.target.querySelector('.submit-btn');
  const originalText = submitBtn.textContent;

  try {
    submitBtn.disabled = true;
    submitBtn.textContent = '⏳ Envoi...';

      const response = await fetch('/coffee-break', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        developerName: devName,
        coffeeType: coffeeType
      })
    });

    if (!response.ok) {
      throw new Error(`Erreur HTTP: ${response.status}`);
    }

    document.getElementById('coffeeForm').reset();
    submitBtn.textContent = '✅ Envoyé !';
    setTimeout(() => {
      submitBtn.textContent = originalText;
      submitBtn.disabled = false;
    }, 2000);

  } catch (error) {
    console.error('Erreur lors de la soumission:', error);
    submitBtn.textContent = '❌ Erreur';
    setTimeout(() => {
      submitBtn.textContent = originalText;
      submitBtn.disabled = false;
    }, 2000);
  }
}

connectSSE();

window.addEventListener('beforeunload', () => {
  if (eventSource) {
    eventSource.close();
  }
});
