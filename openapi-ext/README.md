# OpenAPI Extentions

This adds [Swagger UI](https://swagger.io/tools/swagger-ui/) to MicroProfile Open API.

## Usage

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>openapi-ext</artifactId>
        <version>1.0.6</version>
    </dependency>

* example: http://localhost:8080/example/api/openapi-ui/
* so: *your_scheme://your_domain:your_port/your_context_root/your_application_path*/openapi-ui/

You can change some values using MicroProfile config API:

* **openapi-ui.copyrightBy** - Adds a name to the copyright in the footer. Default to none.
* **openapi-ui.copyrightYear** - Adds the copyright year. Default to current year.
* **openapi-ui.title** - Adds the title in the window. Default to "MicroProfile - Open API".
* **openapi-ui.serverInfo** - Adds info on the server. Default to the system server info.
* **openapi-ui.contextRoot** - Adds the context root. Default to the current value.
* **openapi-ui.swaggerUiTheme** - Use a theme from [swagger-ui-themes](http://meostrander.com/swagger-ui-themes/). Default to "flattop".
* **openapi-ui.swaggerHeaderVisibility** - Show/hide the swagger logo header. Default to "visible".
* **openapi-ui.exploreFormVisibility** - Show/hide the explore form. Default to "hidden".
* **openapi-ui.serverVisibility** - Show/hide the server selection. Default to "hidden".
* **openapi-ui.createdWithVisibility** - Show/hide the created with footer. Default to "visible".

#### Icon

To change the default MicroProfile icon, include a file named ```openapi.png``` in ```/src/main/resources/```

#### Screenshot

![screenshot](https://raw.githubusercontent.com/phillip-kruger/microprofile-extentions/master/openapi-ext/screenshot.png)