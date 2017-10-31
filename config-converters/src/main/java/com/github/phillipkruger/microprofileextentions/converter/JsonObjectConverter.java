package com.github.phillipkruger.microprofileextentions.converter;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.eclipse.microprofile.config.spi.Converter;

/**
 * Converts a json string to a JSonObject
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
public class JsonObjectConverter implements Converter<JsonObject> {

    @Override
    public JsonObject convert(String input) throws IllegalArgumentException {
        try(JsonReader jsonReader = Json.createReader(new StringReader(input))){
            return jsonReader.readObject();
        }
    }
    
}
