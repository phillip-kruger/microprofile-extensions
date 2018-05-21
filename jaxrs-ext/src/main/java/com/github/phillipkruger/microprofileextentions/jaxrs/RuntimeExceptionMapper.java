package com.github.phillipkruger.microprofileextentions.jaxrs;

import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.Config;

/**
 * Translate Runtime exceptions to HTTP response
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 * 
 * Because we do not know if all classes is available at runtime (fractions in wildfly-swarm) we can not annotate all mappers with @Provider.
 * So this RuntimeExeptionMapper will try and use the correct mapper at runtime.
 * 
 * If it can not find any mapper, it will fallback to:
 *  500 Internal Server Error - A generic error message, given when an unexpected condition was encountered and no more specific message is suitable
 * 
 */
@Log
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    
    @Inject
    private Config config;
    
    @Context Providers providers;
    
    @Override
    @Produces(MediaType.WILDCARD)
    public Response toResponse(RuntimeException exception) {
        
        // First try dynamic configured 
        String configkey = "exceptionmapper." + exception.getClass().getName();
        Optional<Integer> posibleDynamicMapperValue = config.getOptionalValue(configkey,Integer.class);
        if(posibleDynamicMapperValue.isPresent()){
            int status = posibleDynamicMapperValue.get();
            return Response.status(status).header(REASON, exception.getMessage()).build();
        } else {
        
            // Handle some included mappers
            switch (exception.getClass().getName()) {
                case CONSTRAINT_VIOLATION:
                    return validationExceptionMapper.toResponse(exception);
                case EJB:
                    return ejbExceptionMapper.toResponse(providers,exception);
                default:
                    return unknownRuntimeResponse(exception);
            }
        }
        
        
    }
    
    private Response unknownRuntimeResponse(RuntimeException exception) {
        final Throwable cause = exception.getCause();
        if (cause != null && providers!=null) {
            final ExceptionMapper mapper = providers.getExceptionMapper(cause.getClass());
            if (mapper != null) {
                return mapper.toResponse(cause);
            } else if (cause instanceof WebApplicationException) {
                return ((WebApplicationException) cause).getResponse();
            }
            // We did not map the cause exception.
            // So this is a server error
            return noMapResponse(cause);
        }
        return noMapResponse(exception);
    }
    
    private Response noMapResponse(final Throwable exception){
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header(REASON, exception.getMessage()).build();
    }
    
    private static final String REASON = "reason";
    
    private static final String CONSTRAINT_VIOLATION = "javax.validation.ConstraintViolationException";
    private static final String EJB = "javax.ejb.EJBException";
    
    private final ValidationExceptionMapper validationExceptionMapper = new ValidationExceptionMapper();
    private final EJBExceptionMapper ejbExceptionMapper = new EJBExceptionMapper();
    
}