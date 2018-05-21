package com.github.phillipkruger.microprofileextentions.jaxrs;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Translate Java null pointer exceptions to HTTP response
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 * 
 * 204 No Content: The server successfully processed the request and is not returning any content
 */
@Provider
public class NullPointerExceptionMapper implements ExceptionMapper<NullPointerException> {
    
    @Override
    @Produces(MediaType.WILDCARD)
    public Response toResponse(NullPointerException nullPointerException) {
        return Response.status(Response.Status.NO_CONTENT).header(REASON,nullPointerException.getMessage()).build();
    }
    
    private static final String REASON = "reason";
}