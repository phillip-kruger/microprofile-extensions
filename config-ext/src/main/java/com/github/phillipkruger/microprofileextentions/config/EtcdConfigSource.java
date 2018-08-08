package com.github.phillipkruger.microprofileextentions.config;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * etcd config source
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@NoArgsConstructor
public class EtcdConfigSource implements ConfigSource {
    
    public static final String NAME = "EtcdConfigSource";
    
    @Override
    public int getOrdinal() {
        return 320;
    }
    
    @Override
    public Map<String, String> getProperties() {
        Map<String,String> m = new HashMap<>();
        ByteSequence bsKey = ByteSequence.fromString("");
        CompletableFuture<GetResponse> getFuture = getClient().getKVClient().get(bsKey);
        try {
            GetResponse response = getFuture.get();
            List<KeyValue> kvs = response.getKvs();
            
            for(KeyValue kv:kvs){
                String key = kv.getKey().toStringUtf8();
                String value = kv.getValue().toStringUtf8();
                m.put(key, value);
            }
        } catch (InterruptedException | ExecutionException ex) {
            log.log(Level.WARNING, "Can not get all config keys and values from etcd Config source: {1}", new Object[]{ex.getMessage()});
        }
        
        return m;
    }

    @Override
    public String getValue(String key) {
        ByteSequence bsKey = ByteSequence.fromString(key);
        CompletableFuture<GetResponse> getFuture = getClient().getKVClient().get(bsKey);
        try {
            GetResponse response = getFuture.get();
            String value = toString(response);
            return value;
        } catch (InterruptedException | ExecutionException ex) {
            log.log(Level.WARNING, "Can not get config value for [{0}] from etcd Config source: {1}", new Object[]{key, ex.getMessage()});
        }
        
        return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
    
    private String toString(GetResponse response){
        if(response.getCount()>0){
            return response.getKvs().get(0).getValue().toStringUtf8();
        }
        return null;
    }
    
    private Client client = null;
    private Client getClient(){
        if(this.client == null){
            log.info("Loading [etcd] MicroProfile ConfigSource");
        
            String scheme = getPropertyValue(KEY_SCHEME,DEFAULT_SCHEME);
            String host = getPropertyValue(KEY_HOST,DEFAULT_HOST);
            String port = getPropertyValue(KEY_PORT,DEFAULT_PORT);
            
            String endpoint = String.format("%s://%s:%s",scheme,host,port);
            log.log(Level.INFO, "Using [{0}] as etcd server endpoint", endpoint);
            this.client = Client.builder().endpoints(endpoint).build();
        }
        return this.client;
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
    
    private static final String KEY_SCHEME = "configsource.etcd.scheme";
    private static final String DEFAULT_SCHEME = "http";
    
    private static final String KEY_HOST = "configsource.etcd.host";
    private static final String DEFAULT_HOST = "localhost";
    
    private static final String KEY_PORT = "configsource.etcd.port";
    private static final String DEFAULT_PORT = "2379";
    
}
