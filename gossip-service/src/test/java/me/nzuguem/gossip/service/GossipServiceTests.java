package me.nzuguem.gossip.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GossipServiceTests {

    @Autowired
    MockMvcTester mockMvcTester;

    @Test
    void should_get_gossip() {
        this.mockMvcTester
                .get()
                .uri("/gossip")
                .queryParam("developerName", "Test")
                .assertThat()
                .hasStatusOk();
    }
}
