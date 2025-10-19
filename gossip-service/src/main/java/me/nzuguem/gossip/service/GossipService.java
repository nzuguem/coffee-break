package me.nzuguem.gossip.service;

import me.nzuguem.gossip.models.GetGossipResponse;
import me.nzuguem.gossip.dao.Gossips;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/gossip")
public class GossipService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GossipService.class);

    @GetMapping
    public ResponseEntity<GetGossipResponse> getGossip(@RequestParam(name = "developerName", required = false, defaultValue = "Anonymous") String developerName) {

        LOGGER.info("👂🏽 {} écoute les potins...", developerName);

        var gossip = Gossips.randomGossip();

        if (LocalDateTime.now().getDayOfWeek().getValue() == 5
                && ThreadLocalRandom.current().nextBoolean()) {
                gossip = "🎉 C'est vendredi ! Mais surtout, ne déploie RIEN.";
        }

        return ResponseEntity.ok(GetGossipResponse.of(gossip, developerName));
    }

}
