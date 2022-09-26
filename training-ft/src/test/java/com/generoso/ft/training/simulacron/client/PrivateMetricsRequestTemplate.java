package com.generoso.ft.training.simulacron.client;

import com.generoso.ft.training.simulacron.client.model.Endpoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Qualifier("private")
public class PrivateMetricsRequestTemplate extends RequestTemplate {

    public PrivateMetricsRequestTemplate(@Value("${service.host}") String host,
                                         @Value("${service.context-path:}") String contextPath) {
        super(host, contextPath);
    }

    @Override
    public Endpoint getEndpoint() {
        return Endpoint.PRIVATE_METRICS;
    }

    @Override
    public Map<String, String> defaultHeaders() {
        return new HashMap<>();
    }
}
