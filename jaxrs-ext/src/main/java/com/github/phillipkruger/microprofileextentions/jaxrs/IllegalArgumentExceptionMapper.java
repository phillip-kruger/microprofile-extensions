package com.github.phillipkruger.microprofileextentions.jaxrs;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Translate Java illegal argument exceptions to HTTP response
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 * 
 * 412 Precondition Failed (RFC 7232): The server does not meet one of the preconditions that the requester put on the request
 */
@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    
    @Override
    @Produces(MediaType.WILDCARD)
    public Response toResponse(IllegalArgumentException illegalArgumentException) {
        return Response.status(Response.Status.PRECONDITION_FAILED).header(REASON,illegalArgumentException.getMessage()).build();
    }
    
    private static final String REASON = "reason";
}