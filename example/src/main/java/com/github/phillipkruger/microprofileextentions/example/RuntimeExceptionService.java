package com.github.phillipkruger.microprofileextentions.example;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.java.Log;
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
@Path("/exception")
@Tag(name = "Simulate some exeption", description = "Create some exception")
public class RuntimeExceptionService {
    
    @GET
    @Operation(description = "Throw an exeption")
    @APIResponses({
        @APIResponse(responseCode = "412", description = "You made a mistake")
    })
    @Produces(MediaType.TEXT_PLAIN)
    public Response doSomething() {
        throw new java.lang.IllegalArgumentException("You made some mistake. Try again maybe ?");
    }
    
}