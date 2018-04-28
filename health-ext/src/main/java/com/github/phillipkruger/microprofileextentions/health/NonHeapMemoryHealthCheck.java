package com.github.phillipkruger.microprofileextentions.health;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

/**
 * Checking memory usage against available memory
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Health
@ApplicationScoped
public class NonHeapMemoryHealthCheck implements HealthCheck {
    
    @Inject @ConfigProperty(name = "health.memory.maxpercentage", defaultValue = "0.9")
    private double maxPercentage;
    
    @Override
    public HealthCheckResponse call() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long memUsed = memoryBean.getNonHeapMemoryUsage().getUsed();
        long memMax = memoryBean.getNonHeapMemoryUsage().getMax();

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("non-heap-memory")
                .withData("used", memUsed)
                .withData("max", memMax)
                .withData("max %", String.valueOf(maxPercentage));

        if(memMax > 0){
            boolean status = (memUsed < memMax * maxPercentage);
            return responseBuilder.state(status).build();
        }else{
            // Max not available
            return responseBuilder.up().build();
        }
    }
}
