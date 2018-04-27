package com.github.phillipkruger.microprofileextentions.config.converter;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.microprofile.config.spi.Converter;

/**
 * Converts a comma separated string to a String array
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
public class ArrayConverter implements Converter<String[]> {

    @Override
    public String[] convert(String input) throws IllegalArgumentException {
        if(isNullOrEmpty(input))return new String[]{};
        
        Stream<String> stream = Stream.of(input);
        return stream.collect(Collectors.toList()).toArray(new String[]{});
    }
    
    /**
      * Not to sure about this, got an javax.json.stream.JsonParsingException in Wildfly with a value of org.eclipse.microprofile.config.configproperty.unconfigureddvalue
    **/
    private boolean isNullOrEmpty(String input){
        return input==null || input.isEmpty() || input.equals(UNCONFIGURED_VALUE);
    }
    
    private static final String UNCONFIGURED_VALUE = "org.eclipse.microprofile.config.configproperty.unconfigureddvalue";
}
