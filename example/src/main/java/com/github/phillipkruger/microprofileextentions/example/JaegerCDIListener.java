package com.github.phillipkruger.microprofileextentions.example;

import fish.payara.micro.cdi.Outbound;
import fish.payara.notification.eventbus.EventbusMessage;
import fish.payara.notification.requesttracing.RequestTrace;
import fish.payara.notification.requesttracing.RequestTraceSpan;
import fish.payara.notification.requesttracing.RequestTracingNotificationData;
import io.jaegertracing.Configuration;
import io.opentracing.Span;
import io.opentracing.Tracer;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Log
@ApplicationScoped
public class JaegerCDIListener {

//    @Inject @ConfigProperty(name = "JAEGER_SERVICE_NAME", defaultValue = "Phillip")
//    private String serviceName;
//    
//    @PostConstruct
//    public void init(){
//        System.getProperties().put("JAEGER_SERVICE_NAME", serviceName);
//    }
    
//    @Inject
//    io.opentracing.Tracer configuredTracer;
    
    public void observe(@Observes @Outbound EventbusMessage event) {

        if (event.getData() instanceof RequestTracingNotificationData) {
            RequestTracingNotificationData requestTracingNotificationData = event.getData().as(RequestTracingNotificationData.class);
            RequestTrace payaraTrace = requestTracingNotificationData.getRequestTrace();
            
            
            log.severe("=======================================================\n"
                    + payaraTrace.toString() + "\n"
                    + "=======================================================");
            
            // or event.getMessage(); ??
            
            Tracer tracer = Configuration.fromEnv().getTracer();
            
            for(RequestTraceSpan payaraSpan: payaraTrace.getTraceSpans()){
                Span span = tracer.buildSpan(payaraSpan.getEventName()).start();
                span.finish();
            }
            
            

        
            
            
            
        }

    }
    
}
