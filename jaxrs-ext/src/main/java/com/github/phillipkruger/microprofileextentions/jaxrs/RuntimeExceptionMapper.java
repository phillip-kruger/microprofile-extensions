package com.github.phillipkruger.microprofileextentions.jaxrs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
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
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Translate Runtime exceptions to HTTP response
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 * 
 * This mapper use MicroProfile Config to look for exceptionmapper.some.exception.class to find the HTTP Response Code to use
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
    
    @Context 
    private Providers providers;
    
    @Inject @ConfigProperty(name = "jaxrs-ext.includeClassName", defaultValue = "false")
    private boolean includeClassName;
    
    @Inject @ConfigProperty(name = "jaxrs-ext.stacktraceLogLevel", defaultValue = "FINEST")
    private String stacktraceLogLevel;
    
    @Override
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response toResponse(RuntimeException exception) {
        return handleThrowable(exception);
    }
    
    private Response handleThrowable(Throwable exception) {
        if(exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }
        if(exception!=null){
            String configkey = exception.getClass().getName() + STATUS_CODE_KEY;
            Optional<Integer> possibleDynamicMapperValue = config.getOptionalValue(configkey,Integer.class);
            if(possibleDynamicMapperValue.isPresent()){
                int status = possibleDynamicMapperValue.get();
                // You switched it off
                if(status<0)return handleNotMapped(exception);
                String reason = getReason(exception);
                log.log(getLevel(), reason, exception);
                return Response.status(status).header(REASON, reason).build();
            } else if(exception.getCause()!=null && providers!=null){
                final Throwable cause = exception.getCause();
                return handleThrowable(cause);
            } else {
                return handleNotMapped(exception);
            }
        }
        return handleNullException();
    }
    
    private String getReason(Throwable exception){
        String reason = exception.getMessage();
        if(reason==null || reason.isEmpty()){
            // Lets try the cause ?
            final Throwable cause = exception.getCause();
            if (cause != null){
                return getReason(cause);
            }else{
                return constructReason(exception, "Unknown exception");
            }
        }
        return constructReason(exception,reason);
    }
    
    private String constructReason(Throwable exception, String message){
        String premessage = "";
        if(includeClassName)premessage = "[" + exception.getClass().getName() + "] ";
        return premessage + message;
    }
    
    private Response handleNotMapped(final Throwable exception){
        log.log(getLevel(), "Unmapped Runtime Exception", exception);
        
        List<String> reasons = getReasons(exception, new ArrayList<>());
        
        Response.ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        for(String reason:reasons){
            builder = builder.header(REASON, reason);
        }
        
        return builder.build();
    }
    
    private Response handleNullException(){
        log.log(getLevel(), "Runtime Exception that is null");
        Response.ResponseBuilder builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        return builder.build();
    }
    
    private List<String> getReasons(final Throwable exception,List<String> reasons){
        if(exception.getMessage()!=null)reasons.add(exception.getMessage());
        if(exception.getCause()!=null){
            return getReasons(exception.getCause(), reasons);
        } else {
            return reasons;
        }
        
    }
    
    private Level getLevel(){
        return Level.parse(stacktraceLogLevel);
    }
    
    private static final String REASON = "reason";
    private static final String STATUS_CODE_KEY = "/mp-jaxrs-ext/statuscode";
}