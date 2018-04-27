package com.github.phillipkruger.microprofileextentions.health;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

@Health
@ApplicationScoped
public class NonHeapMemoryHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long memUsed = memoryBean.getNonHeapMemoryUsage().getUsed();
        long memMax = memoryBean.getNonHeapMemoryUsage().getMax();

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("non-heap-memory")
                .withData("used", memUsed)
                .withData("max", memMax);

        boolean status = (memUsed < memMax * 0.9);

        return responseBuilder.state(status).build();

    }
}
