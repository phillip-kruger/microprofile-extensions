package com.github.phillipkruger.microprofileextentions.config;

import java.util.HashMap;
import java.util.Map;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * In memory config
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@NoArgsConstructor
public class MemoryConfigSource implements ConfigSource {
    
    public static final String NAME = "MemoryConfigSource";
    private static final Map<String,String> PROPERTIES = new HashMap<>();
    
    @Override
    public int getOrdinal() {
        return 500;
    }
    
    @Override
    public Map<String, String> getProperties() {
        return PROPERTIES;
    }

    @Override
    public String getValue(String key) {
        if(PROPERTIES.containsKey(key)){
            return PROPERTIES.get(key);
        }
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
    
}
