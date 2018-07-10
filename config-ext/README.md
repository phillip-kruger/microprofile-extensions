# Config API Extentions

This extention gives you some extra configuration sources and some extra converters.

## Usage

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>config-ext</artifactId>
        <version>1.0.6</version>
    </dependency>

## Config sources

### File config

Add config elements in a external properties file (default application.properties)

### Memory config

Set values in memory. Useful when you want to change config during runtime.

You can do this by using the REST API to change the config values:

    GET /config/sources - list all config sources
    GET /config/all - get all configurations
    GET /config/key/{key} - get the configured value for {key}
    PUT /config/key/{key} - set the value for {key}
    DELETE /config/key/{key} - delete the configured value for {key}

![REST API](https://raw.githubusercontent.com/phillip-kruger/microprofile-extentions/master/config-ext/memory_config_api.png)

## Config converters 

Extra MicroProfile converters (JsonArray, JsonObject, List, Array)

Example:

In the java code:

    @Inject
    @ConfigProperty(name = "someJsonKey")
    private JsonArray someValue;

    @Inject
    @ConfigProperty(name = "someListKey")
    private List<String> someValue;

When using the property:

    someJsonKey=["value1","value2","value3"]
    someListKey=value1,value2,value3
    