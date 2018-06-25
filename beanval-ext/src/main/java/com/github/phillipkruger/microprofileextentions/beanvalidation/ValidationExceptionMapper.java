package com.github.phillipkruger.microprofileextentions.beanvalidation;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import lombok.extern.java.Log;

/**
 * Translate Java validation exceptions to HTTP response
 * @author Phillip Kruger (apiee@phillip-kruger.com)
 * 
 * 412 Precondition Failed (RFC 7232): The server does not meet one of the preconditions that the requester put on the request
 */
@Log
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    
    @Override
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response toResponse(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        
        ValidationErrors errors = new ValidationErrors();
        if(violations!=null && !violations.isEmpty()){
            Iterator<ConstraintViolation<?>> i = violations.iterator();
            while (i.hasNext()) {
                ConstraintViolation<?> violation = i.next();
                if(violation!=null){
                    ValidationError ve = new ValidationError();
                    ve.setMessage(violation.getMessage());
                    if(violation.getPropertyPath()!=null)
                        ve.setPath(violation.getPropertyPath().toString());
                    if(violation.getInvalidValue()!=null)
                        ve.setInvalidValue(violation.getInvalidValue().toString());
                    errors.getValidationError().add(ve);
                }
            }
        }else{
           String message = constraintViolationException.getMessage();
           errors.getValidationError().add(new ValidationError(message,EMPTY,EMPTY));
        }
        return Response.status(Response.Status.PRECONDITION_FAILED)
                .header(REASON, constraintViolationException.getMessage()).entity(errors).build();
    }
    
    private static final String REASON = "reason";
    private static final String EMPTY = "";
}
