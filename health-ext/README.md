# Health API Extentions

This extention gives you some default health endpoints that can be configured using the Config Api

## Usage

    <dependency>
        <groupId>com.github.phillip-kruger.microprofile-extentions</groupId>
        <artifactId>health-ext</artifactId>
        <version>1.0.6</version>
    </dependency>

Go to /health to see all default providers

* HeapMemoryHealthCheck - this will report DOWN when the heap memory reach a certain percentage of total heap available. Default to 90% (0.9). You can configure this value with the Config API (health.heapmemory.maxpercentage)
* NonHeapMemoryHealthCheck - this will report DOWN when the memory reach a certain percentage of total available memory. Default to 90% (0.9). You can configure this value with the Config API (health.memory.maxpercentage)
* SystemLoadHealthCheck - this will report DOWN when the system load reach a certain percentage. Default to 70% (0.7). You can configure this value with the Config API (health.systemload.maxpercentage)
* ThreadHealthCheck - this will report DOWN when a certain number of threads are reached. Default to 9999999. You can configure this value with the Config API (health.threadcount.max)