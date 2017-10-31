package com.github.phillipkruger.microprofileextentions.converter;

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
        Stream<String> language = Stream.of(input);
        return language.collect(Collectors.toList()).toArray(new String[]{});
    }
    
}
