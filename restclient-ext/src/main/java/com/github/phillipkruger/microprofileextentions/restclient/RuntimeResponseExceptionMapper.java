package com.github.phillipkruger.microprofileextentions.restclient;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Log
@Provider
public class RuntimeResponseExceptionMapper implements ResponseExceptionMapper<RuntimeException>{

    @Inject
    private Config config;
    
    private List<Integer> codes = new ArrayList<>();
    
    @PostConstruct
    public void init(){
        Iterable<String> propertyNames = config.getPropertyNames();
        for(String property : propertyNames){
            if(property.endsWith(PROPERTY_KEY_END)){
                String code = property.split(SLASH)[0];
                if(isNumeric(code))codes.add(Integer.valueOf(code));
            }
        }
    }
    
    @Override
    public RuntimeException toThrowable(Response response) {
        String reason = getReason(response);
        int status = response.getStatus();
        String key = status + PROPERTY_KEY_END;
        
        String exceptionClassName = config.getValue(key, String.class);
        
        log.log(Level.SEVERE, ">>>> Mapping {0} to {1}", new Object[]{status, exceptionClassName});
        
        return instanciateRuntimeException(exceptionClassName,reason);

    }

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return codes.contains(status);
    }
    
    private String getReason(Response response){
        String reason = response.getHeaderString(REASON);
        if(reason==null || reason.isEmpty()){
            Response.StatusType status = response.getStatusInfo();
            reason = status.getReasonPhrase();
        }
        return reason;
    }
    
    private RuntimeException instanciateRuntimeException(String exceptionClassName,String reason){
        try {
            Class exceptionClass = Class.forName(exceptionClassName);
            Class[] types = {String.class};
            Constructor constructor = exceptionClass.getConstructor(types);
            Object[] parameters = {reason};
            Object instanceOfException = constructor.newInstance(parameters);
            
            return (RuntimeException)instanceOfException;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            log.log(Level.SEVERE, "Could not create Exception with reason [{0}], falling back to general Runtime exception", reason);
            return new RuntimeException(reason, ex);
        }
    }
    
    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()){
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    
    private static final String REASON = "reason";
    private static final String PROPERTY_KEY_END = "/mp-restclient-ext/exception";
    private static final String SLASH = "/";
}
