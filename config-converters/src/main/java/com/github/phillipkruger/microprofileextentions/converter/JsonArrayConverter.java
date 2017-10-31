package com.github.phillipkruger.microprofileextentions.converter;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import org.eclipse.microprofile.config.spi.Converter;

/**
 * Converts a json string to a JSonArray
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
public class JsonArrayConverter implements Converter<JsonArray> {

    @Override
    public JsonArray convert(String input) throws IllegalArgumentException {
        try(JsonReader jsonReader = Json.createReader(new StringReader(input))){
            return jsonReader.readArray();
        }
    }
    
}
