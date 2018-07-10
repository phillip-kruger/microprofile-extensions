# REST Client Extentions

This allows you to use the Config API to map HTTP Response code to RuntimeExceptions.

## Usage

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>restclient-ext</artifactId>
        <version>1.0.6</version>
    </dependency>

## Mapping example:

To map a HTTP 412 Response status to a javax.validation.ConstraintViolationException:

    412/mp-restclient-ext/exception=javax.validation.ConstraintViolationException

If the HTTP Headers contain a reason header, this will be included in the exception message.