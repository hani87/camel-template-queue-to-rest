package com.example.integration.routes;

import com.example.integration.config.RestEndpointsConfig;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class QueueToRestRoute extends RouteBuilder {

    private final RestEndpointsConfig config;

    public QueueToRestRoute(RestEndpointsConfig config) {
        this.config = config;
    }

    @Override
    public void configure() throws Exception {
        errorHandler(deadLetterChannel("activemq:queue:ERROR.ORDERS")
                .useOriginalMessage()
                .maximumRedeliveries(3)
                .redeliveryDelay(2000)
                .backOffMultiplier(2));

        from("activemq:queue:{{incoming.queue}}")
                .routeId("queue-to-rest")
                .log("📩 Received: ${body}")
                .multicast().parallelProcessing()
                .log("endpoints-------------" + Arrays.toString(config.getEndpoints().toArray(new String[0])))
                    .to(config.getEndpoints().toArray(new String[0]))
                    .log("✅ Sent to all REST endpoints")
                .end()
                ;
    }
}
