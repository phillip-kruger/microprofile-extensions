package com.github.phillipkruger.microprofileextentions.jaxrs;

import java.util.Iterator;
import java.util.Set;
import javax.ws.rs.core.Response;

/**
 * Translate Java validation exceptions to HTTP response
 * @author Phillip Kruger (apiee@phillip-kruger.com)
 * 
 * 412 Precondition Failed (RFC 7232): The server does not meet one of the preconditions that the requester put on the request
 */
public class ValidationExceptionMapper {
    private static final String EMPTY = "";
    
    public Response toResponse(RuntimeException exception) {
        Set<javax.validation.ConstraintViolation<?>> violations = ((javax.validation.ConstraintViolationException)exception).getConstraintViolations();
        
        ValidationErrors errors = new ValidationErrors();
        // Real jsr validation errors
        if(violations!=null && !violations.isEmpty()){
            Iterator<javax.validation.ConstraintViolation<?>> i = violations.iterator();
            while (i.hasNext()) {
                javax.validation.ConstraintViolation<?> violation = i.next();
                if(violation!=null){
                    ValidationError ve = new ValidationError();
                    ve.setMessage(violation.getMessage());
                    if(violation.getPropertyPath()!=null)ve.setPath(violation.getPropertyPath().toString());
                    if(violation.getInvalidValue()!=null)ve.setInvalidValue(violation.getInvalidValue().toString());
                    errors.getValidationError().add(ve);
                }
            }// We throw the exception
        }else{
           String message = exception.getMessage();
           errors.getValidationError().add(new ValidationError(message,EMPTY,EMPTY));
        }
        return Response.status(Response.Status.PRECONDITION_FAILED).header(REASON, exception.getMessage()).entity(errors).build();
    }
    
    private static final String REASON = "reason";
}
