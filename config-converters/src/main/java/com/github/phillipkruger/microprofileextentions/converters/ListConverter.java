package com.github.phillipkruger.microprofileextentions.converters;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.microprofile.config.spi.Converter;

/**
 * Converts a comma separated string to a list
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
public class ListConverter implements Converter<List<String>> {

    @Override
    public List<String> convert(String input) {
        Stream<String> language = Stream.of(input);
        return language.collect(Collectors.toList());
    }
    
}
