# Microprofile extentions

> Some extentions for MicroProfile

[![Build Status](https://travis-ci.org/phillip-kruger/microprofile-extentions.svg?branch=master)](https://travis-ci.org/phillip-kruger/microprofile-extentions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.phillip-kruger/microprofile-extentions/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.phillip-kruger/microprofile-extentions)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/phillip-kruger/microprofile-extentions/master/LICENSE)

[![Twitter URL](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/phillipkruger)

> phillip.kruger@phillip-kruger.com

Tested with 

* [Open Liberty](https://openliberty.io/)
* [Wildfly swarm](http://wildfly-swarm.io/)
* [Payara](https://www.payara.fish/)

## Config API Extentions

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>config-ext</artifactId>
        <version>1.0.4</version>
    </dependency>

### File config

Add config elements in a Properties file (application.properties)

### Memory config

Set values in memory. Useful when you want to change config during runtime.


REST API available:

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
        <version>1.0.4</version>
    </dependency>

Go to /health to see all default providers

* HeapMemoryHealthCheck - this will report DOWN when the heap memory reach a certain percentage of total heap available. Default to 90% (0.9). You can configure this value with the Config API (health.heapmemory.maxpercentage)
* NonHeapMemoryHealthCheck - this will report DOWN when the memory reach a certain percentage of total available memory. Default to 90% (0.9). You can configure this value with the Config API (health.memory.maxpercentage)
* SystemLoadHealthCheck - this will report DOWN when the system load reach a certain percentage. Default to 70% (0.7). You can configure this value with the Config API (health.systemload.maxpercentage)
* ThreadHealthCheck - this will report DOWN when a certain number of threads are reached. Default to 9999999. You can configure this value with the Config API (health.threadcount.max)

## OpenAPI Extentions

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>openapi-ext</artifactId>
        <version>1.0.4</version>
    </dependency>

This adds Swagger UI to MicroProfile Open API. 

* example: http://localhost:8080/example/api/openapi-ui/
* */your_context_root/your_application_path*/openapi-ui/

You can change some values using MicroProfile config API:

* openapi-ui.copyrightBy - Adds a name to the copyright in the footer. Default to none.
* openapi-ui.copyrightYear - Adds the copyright year. Default to current year.
* openapi-ui.title - Adds the title in the window. Default to "MicroProfile - Open API".
* openapi-ui.serverInfo - Adds info on the server. Default to the system server info.
* openapi-ui.contextRoot - Adds the context root. Default to the current value.
* openapi-ui.swaggerUiTheme - Use a theme from [swagger-ui-themes](http://meostrander.com/swagger-ui-themes/). Default to "flattop".
* openapi-ui.swaggerHeaderVisibility - Show/hide the swagger logo header. Default to "visible".
* openapi-ui.exploreFormVisibility - Show/hide the explore form. Default to "hidden".
* openapi-ui.serverVisibility - Show/hide the server selection. Default to "hidden".
* openapi-ui.createdWithVisibility- Show/hide the created with footer. Default to "visible".

#### Screenshot