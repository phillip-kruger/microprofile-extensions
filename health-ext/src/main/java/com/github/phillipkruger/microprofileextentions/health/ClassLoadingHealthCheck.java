package com.github.phillipkruger.microprofileextentions.health;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

@Health
@ApplicationScoped
public class ClassLoadingHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();

        int loadedClassCount = classLoadingMXBean.getLoadedClassCount();
        long totalLoadedClassCount = classLoadingMXBean.getTotalLoadedClassCount();
        long unloadedClassCount = classLoadingMXBean.getUnloadedClassCount();

        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("class-loading")
                .withData("loaded", loadedClassCount)
                .withData("unloaded", unloadedClassCount)
                .withData("total", totalLoadedClassCount);

        boolean status = true; // ?

        return responseBuilder.state(status).build();

    }
}
