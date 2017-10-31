package com.github.phillipkruger.microprofileextentions.memoryconfig;

import java.util.NoSuchElementException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 * Map a NoSuchElement to a Not Found
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Provider
public class NoSuchElementMapper implements ExceptionMapper<NoSuchElementException>{
    
    private static final String REASON = "Reason";

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(NoSuchElementException noSuchElementException) {
        return Response.status(Response.Status.NOT_FOUND).header(REASON,noSuchElementException.getMessage()).build();
    }
}