package com.github.phillipkruger.microprofileextentions.health;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

@Health
@ApplicationScoped
public class GarbageCollectorHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("garbage-collection");
        
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean garbageCollectorMXBean:garbageCollectorMXBeans){
            String name = garbageCollectorMXBean.getName();
            long collectionCount = garbageCollectorMXBean.getCollectionCount();
            long collectionTime = garbageCollectorMXBean.getCollectionTime();
            responseBuilder = responseBuilder.withData(name + " collection count", collectionCount);
            responseBuilder = responseBuilder.withData(name + " collection time", collectionTime);
        }
        
        boolean status = true; //?

        return responseBuilder.state(status).build();

    }
}
