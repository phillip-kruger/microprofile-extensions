package com.github.phillipkruger.microprofileextentions.jwt;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import lombok.extern.java.Log;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Intercept a method and check that the user are allowed to access it
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@UserAccess
@Interceptor
@Log
public class UserAccessInterceptor implements Serializable {

    @Context
    private SecurityContext securityContext;
    
    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {
        Object response = ic.proceed();
        
        // Not to worry if there is no response ?
        if(response==null)return response;
        
        Method method = ic.getMethod();
        UserAccess annotation = method.getAnnotation(UserAccess.class);
        String[] ignoreGroups = annotation.ignoreGroups();
        
        // Ignore for certain groups.
        if(shouldIgnore(ignoreGroups))return response;
        
        Principal userPrincipal = securityContext.getUserPrincipal();
        if(userPrincipal!=null && userPrincipal.getName()!=null && !userPrincipal.getName().isEmpty()){
            Object compareThis = response;
            String pathToUserName = annotation.pathToUserName();
            if(pathToUserName!=null && !pathToUserName.isEmpty()){
                compareThis = getPropertyValue(response, pathToUserName).toString();
            }
            
            if(compareThis.equals(userPrincipal.getName()))return response;
            throw new NotAuthorizedException("User [" + userPrincipal.getName() + "] not authorized to request " + ic.getMethod().getName());
        }
        throw new NotAuthorizedException("No user logged in. Not authorized to request " + ic.getMethod().getName());
    }
    
    private boolean shouldIgnore(String[] ignoreGroups){
        if(ignoreGroups!=null && ignoreGroups.length > 0){
            List<String> ignoreGroupsList = Arrays.asList(ignoreGroups);
            for(String ignoreGroup : ignoreGroupsList){
                if(securityContext.isUserInRole(ignoreGroup))return true;
            }
        }
        return false;
    }
    
    private Object getPropertyValue(Object bean,String property){
        try {
            return PropertyUtils.getProperty(bean, property);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}