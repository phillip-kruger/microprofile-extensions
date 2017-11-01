package com.github.phillipkruger.microprofileextentions.cdifilter;

import javax.enterprise.util.AnnotationLiteral;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
public class FilterLiteral extends AnnotationLiteral<Filter> implements Filter {

    private Class<?> forClass;
    
    public FilterLiteral(String className) {
        try {
            this.forClass = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public Class<?> forClass(){
        return this.forClass;
    }
    
}