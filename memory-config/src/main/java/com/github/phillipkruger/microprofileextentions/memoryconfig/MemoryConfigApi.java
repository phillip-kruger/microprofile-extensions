package com.github.phillipkruger.microprofileextentions.memoryconfig;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * Expose the config
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@Path("/config")
public class MemoryConfigApi {
 
    @Inject
    private Config config;
    
    private ConfigSource memoryConfigSource;
    
    @PostConstruct
    public void init(){
        this.memoryConfigSource = getMemoryConfigSource();
    }
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(String property:config.getPropertyNames()){
            String value = config.getValue(property, String.class);
            arrayBuilder.add(Json.createObjectBuilder().add(property, value).build());
        }
        return Response.ok(arrayBuilder.build()).build();
    }
    
    @GET
    @Path("/sources")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConfigSources(){
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(ConfigSource source:config.getConfigSources()){
            arrayBuilder.add(Json.createObjectBuilder().add(String.valueOf(source.getOrdinal()), source.getName()).build());
        }
        return Response.ok(arrayBuilder.build()).build();
    }
    
    @GET
    @Path("/key/{key}")
    public Response getValue(@NotNull @PathParam("key") String key){
        return Response.ok(config.getValue(key, String.class)).build();
    }
    
    @PUT
    @Path("/key/{key}")
    public Response setValue(@NotNull @PathParam("key") String key,String value){
        memoryConfigSource.getProperties().put(key, value);
        return Response.accepted().build();
    }

    @DELETE
    @Path("/key/{key}")
    public Response removeValue(@NotNull @PathParam("key") String key){
        memoryConfigSource.getProperties().remove(key);
        return Response.accepted().build();
    }
    
    private ConfigSource getMemoryConfigSource() {
        for(ConfigSource configSource:config.getConfigSources()){
            if(configSource.getName().equals(MemoryConfigSource.NAME))return configSource;
        }
        return null;
    }
    
}
