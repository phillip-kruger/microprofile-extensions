# Microprofile extentions

 * Build status: [![build_status](https://travis-ci.org/phillip-kruger/microprofile-extentions.svg?branch=master)](https://travis-ci.org/phillip-kruger/microprofile-extentions)

Some extentions for MicroProfile

Tested with 

* [Open Liberty](https://openliberty.io/)
* [Wildfly swarm](http://wildfly-swarm.io/)
* [Payara](https://www.payara.fish/)

## Config API Extentions

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>config-ext</artifactId>
        <version>1.0.3</version>
    </dependency>

### File config

TODO

### Memory config

REST Paths avaialble:

    GET /config/sources - list all config sources
    GET /config/all - get all configurations
    GET /config/key/{key} - get the configured value for {key}
    PUT /config/key/{key} - set the value for {key}
    DELETE /config/key/{key} - delete the configured value for {key}

### Config converters 

Extra MicroProfile converters (JsonArray, JsonObject, List, Array)

Example:
    
    @Inject
    @ConfigProperty(name = "someKey")
    private JsonArray someValue;

## Health API Extentions

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>health-ext</artifactId>
        <version>1.0.3</version>
    </dependency>

Go to /health to see all default providers
