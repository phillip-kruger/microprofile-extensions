# JAX-RS Extentions

This allows you to use the Config API to map Runtime Exceptions to HTTP Response codes. Error message will be included in the HTTP 'reason' header.

## Usage

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>jaxrs-ext</artifactId>
        <version>1.0.7</version>
    </dependency>

## Mapping example:

To map a javax.ws.rs.NotAuthorizedException to a 401 status code

    javax.ws.rs.NotAuthorizedException/mp-jaxrs-ext/statuscode=401
    