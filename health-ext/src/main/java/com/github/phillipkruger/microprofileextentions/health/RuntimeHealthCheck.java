package com.github.phillipkruger.microprofileextentions.health;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

@Health
@ApplicationScoped
public class RuntimeHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("runtime")
                .withData("boot classpath", runtimeMXBean.getBootClassPath())
                .withData("classpath", runtimeMXBean.getClassPath())
                .withData("library path", runtimeMXBean.getLibraryPath())
                .withData("management spec version", runtimeMXBean.getManagementSpecVersion())
                .withData("name", runtimeMXBean.getName())
                .withData("spec name", runtimeMXBean.getSpecName())
                .withData("spec vendor", runtimeMXBean.getSpecVendor())
                .withData("start time", runtimeMXBean.getStartTime())
                .withData("up time", runtimeMXBean.getUptime())
                .withData("vm name", runtimeMXBean.getVmName())
                .withData("vm vendor", runtimeMXBean.getVmVendor())
                .withData("vm version", runtimeMXBean.getVmVersion());
        
        boolean status = true; // ?
        
        return responseBuilder.state(status).build();

    }
}
