package com.github.phillipkruger.microprofileextentions.jwt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@Inherited
@InterceptorBinding
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserAccess {
    @Nonbinding String pathToUserName() default "";
    @Nonbinding String[] ignoreGroups() default "";
}