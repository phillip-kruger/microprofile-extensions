package com.github.phillipkruger.microprofileextentions.health;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

/**
 * Checking average load usage against configured max load
 * @see http://blog.scoutapp.com/articles/2009/07/31/understanding-load-averages
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Health
@ApplicationScoped
public class SystemLoadHealthCheck implements HealthCheck {

    @Inject @ConfigProperty(name = "health.systemload.maxpercentage", defaultValue = "0.70")
    private double maxPercentage;
    
    @Override
    public HealthCheckResponse call() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

        String arch = operatingSystemMXBean.getArch();
        String name = operatingSystemMXBean.getName();
        String version = operatingSystemMXBean.getVersion();
        int availableProcessors = operatingSystemMXBean.getAvailableProcessors();

        double systemLoadAverage = operatingSystemMXBean.getSystemLoadAverage();
        double systemLoadAveragePerProcessors = systemLoadAverage / availableProcessors;
        
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("system-load")
                .withData("name", name)
                .withData("arch", arch)
                .withData("version", version)
                .withData("processors", availableProcessors)
                .withData("loadAverage", String.valueOf(systemLoadAverage))
                .withData("loadAverage per processor", String.valueOf(systemLoadAveragePerProcessors))
                .withData("loadAverage %", String.valueOf(maxPercentage));

        if(systemLoadAverage>0){
            boolean status = systemLoadAveragePerProcessors < maxPercentage;
            return responseBuilder.state(status).build();
        }else{
            // Load average not available
            return responseBuilder.up().build();
        }

    }
}
