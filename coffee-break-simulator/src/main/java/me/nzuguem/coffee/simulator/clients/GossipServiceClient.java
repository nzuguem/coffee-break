package me.nzuguem.coffee.simulator.clients;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import me.nzuguem.coffee.simulator.models.GetGossipResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/gossip")
@RegisterRestClient(configKey = "gossip-service")
public interface GossipServiceClient {

    @GET
    GetGossipResponse getGossip(@RestQuery String developerName);
}
