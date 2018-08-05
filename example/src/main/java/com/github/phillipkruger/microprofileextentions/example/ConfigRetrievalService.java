package com.github.phillipkruger.microprofileextentions.example;

import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

/**
 * Example Service. JAX-RS
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@RequestScoped
@Path("/config")
@Tag(name = "Config Retrieval service", description = "Get the value for a certain config")
public class ConfigRetrievalService {
    
    @Inject
    private Config config;
    
    @GET @Path("/{key}")
    @Operation(description = "Get the value for this key")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Successfull, returning foo")
    })
    @Produces(MediaType.TEXT_PLAIN)
    public Response getValue(@PathParam("key") String key) {
        String value = config.getValue(key, String.class);
        log.log(Level.INFO, "{0} = {1}", new Object[]{key, value});
        return Response.ok(value).build();
    }
    
}