package com.generoso.ft.training.simulacron.client.template;

import com.generoso.ft.training.simulacron.client.model.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("service-request")
public class UpdateBookRequestTemplate extends RequestTemplate {

    @Autowired
    public UpdateBookRequestTemplate(@Value("${service.host}") String host,
                                     @Value("${service.context-path:}") String contextPath) {
        super(host, contextPath);
    }

    @Override
    public Endpoint getEndpoint() {
        return Endpoint.UPDATE_BOOK;
    }

    @Override
    public String defaultBody() {
        return """
                {
                	"title": "book title",
                	"publisher": "publisher name"
                }
                """;
    }
}
