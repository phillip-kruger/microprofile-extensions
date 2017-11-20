package com.github.phillipkruger.microprofileextentions.fileconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * File config
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
public class FileConfigSource implements ConfigSource {
    
    public static final String NAME = "FileConfigSource";
    private static final Map<String,String> PROPERTIES = new HashMap<>();
    
    public FileConfigSource(){
        log.severe("============= Now loading Fileconfig source ===============");
        
        String filename;
        String path = System.getProperty(PATH_PROPERTY);
        if(path==null || path.isEmpty()){
            filename = DEFAULT_NAME;
        }else{
            filename = path;
        }
        try {
            log.severe("Looking for property [" + filename + "]");

            Properties p = new Properties();

            File f = new File(filename);
            //if(f.exists() && f.isFile()){
                log.severe("file = " + f.getAbsolutePath());
                p.load(new FileInputStream(f));

                Set<Map.Entry<Object, Object>> entrySet = p.entrySet();

                entrySet.forEach((entry) -> {
                    log.severe(">>>> loading [" + entry.getKey() + "]");
                    PROPERTIES.put((String)entry.getKey(),(String)entry.getValue());
                });
            //}
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public int getOrdinal() {
        return 400;
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
    
    private static final String PATH_PROPERTY = "applicationPropertiesPath";
    private static final String DEFAULT_NAME = "application.properties";
                                                
    
}
