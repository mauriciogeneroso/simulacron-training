package com.generoso.ft.training.simulacron.client.template;

import com.generoso.ft.training.simulacron.client.model.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("service-request")
public class DeleteBookRequestTemplate extends RequestTemplate {

    @Autowired
    public DeleteBookRequestTemplate(@Value("${service.host}") String host,
                                     @Value("${service.context-path:}") String contextPath) {
        super(host, contextPath);
    }

    @Override
    public Endpoint getEndpoint() {
        return Endpoint.DELETE_BOOK;
    }
}
