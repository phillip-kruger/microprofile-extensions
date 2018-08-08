package com.github.phillipkruger.microprofileextentions.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * File config
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@NoArgsConstructor
public class FileConfigSource implements ConfigSource {
    
    public static final String NAME = "FileConfigSource";
    
    @Override
    public int getOrdinal() {
        return 310;
    }
    
    @Override
    public String getValue(String key) {
        if(getProperties().containsKey(key)){
            return getProperties().get(key);
        }
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
    
    private final Map<String,String> properties = new HashMap<>();
    @Override
    public Map<String, String> getProperties() {
        if(this.properties == null){
            log.info("Loading [file] MicroProfile ConfigSource");
            String filename = getPropertyValue(KEY_FILE_PATH,DEFAULT_FILE_PATH);
            log.log(Level.INFO, "Using [{0}] as property file", filename);
            File f = new File(filename);

            try {
                Properties p = new Properties();
                p.load(new FileInputStream(f));
                Set<Map.Entry<Object, Object>> entrySet = p.entrySet();

                entrySet.forEach((entry) -> {
                    this.properties.put((String)entry.getKey(),(String)entry.getValue());
                });

            } catch (IOException ex) {
                log.log(Level.INFO, "FileConfigSource did not find properties file [{0}]", f.getAbsolutePath());
            }
        }
        return this.properties;
    }
    
    private String getPropertyValue(String key,String defaultValue){
        Config config = ConfigProvider.getConfig();
        Iterable<ConfigSource> configSources = config.getConfigSources();
        for(ConfigSource configsource:configSources){
            if(!configsource.getName().equals(NAME)){
                String val = configsource.getValue(key);
                if(val!=null && !val.isEmpty())return val;
            }
        }
        return defaultValue;
        
    }
    
    private static final String KEY_FILE_PATH = "configsource.file.path";
    private static final String DEFAULT_FILE_PATH = "application.properties";
    
}
