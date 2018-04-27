package com.github.phillipkruger.microprofileextentions.health;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

@Health
@ApplicationScoped
public class SystemLoadHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();

        String arch = operatingSystemMXBean.getArch();
        String name = operatingSystemMXBean.getName();
        String version = operatingSystemMXBean.getVersion();
        int availableProcessors = operatingSystemMXBean.getAvailableProcessors();

        double systemLoadAverage = operatingSystemMXBean.getSystemLoadAverage();

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("system-load")
                .withData("name", name)
                .withData("arch", arch)
                .withData("version", version)
                .withData("processors", availableProcessors)
                .withData("systemLoadAverage", String.valueOf(systemLoadAverage));

        boolean status = systemLoadAverage < 90;

        return responseBuilder.state(status).build();

    }
}
