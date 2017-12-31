# Microprofile extentions

 * Build status: [![build_status](https://travis-ci.org/phillip-kruger/microprofile-extentions.svg?branch=master)](https://travis-ci.org/phillip-kruger/microprofile-extentions)

Some extentions for MicroProfile

Tested with 

* [Open Liberty](https://openliberty.io/)
* [Wildfly swarm](http://wildfly-swarm.io/)

## File config 
MicroProfile config from external file

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>file-config</artifactId>
        <version>1.0.2</version>
    </dependency>

## Memory config
MicroProfile config in memory

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>memory-config</artifactId>
        <version>1.0.2</version>
    </dependency>

REST Paths avaialble:

    GET /config/sources - list all config sources
    GET /config/all - get all configurations
    GET /config/key/{key} - get the configured value for {key}
    PUT /config/key/{key} - set the value for {key}
    DELETE /config/key/{key} - delete the configured value for {key}

## Config converters 
Extra MicroProfile converters (JsonArray, JsonObject, List, Array)

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>config-converters</artifactId>
        <version>1.0.2</version>
    </dependency>

Example:
    
    @Inject
    @ConfigProperty(name = "someKey")
    private JsonArray someKey;
    