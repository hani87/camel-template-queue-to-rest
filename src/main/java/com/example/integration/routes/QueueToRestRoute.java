package com.example.integration.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class QueueToRestRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {

        String mockRestUrl = System.getenv("MOCK_REST_URL");

        // Global error handler with retry & DLQ
        errorHandler(deadLetterChannel("activemq:queue:ERROR.ORDERS")
                .useOriginalMessage()
                .maximumRedeliveries(3)
                .redeliveryDelay(2000) // 2 sec
                .backOffMultiplier(2) // exponential backoff
                .retryAttemptedLogLevel(org.apache.camel.LoggingLevel.WARN));

        from("activemq:queue:INCOMING.ORDERS")
                .routeId("queue-to-rest")
                .log("📩 Received: ${body} url: " + mockRestUrl)
                .to(mockRestUrl)
                .log("✅ Sent to REST API");
    }
}
