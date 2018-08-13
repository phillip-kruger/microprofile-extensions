# REST Client extensions

This allows you to use the Config API to map HTTP Response codes to Runtime Exceptions.

## Usage

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extensions</groupId>
        <artifactId>restclient-ext</artifactId>
        <version>1.0.8</version>
    </dependency>

## Mapping example:

To map a HTTP 412 Response status to a javax.validation.ConstraintViolationException:

    412/mp-restclient-ext/exception=javax.validation.ConstraintViolationException

If the HTTP Headers contain a reason header, this will be included in the exception message.