package com.generoso.ft.training.simulacron.client.template;

import com.generoso.ft.training.simulacron.client.model.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("service-request")
public class AddBookRequestTemplate extends RequestTemplate {

    @Autowired
    public AddBookRequestTemplate(@Value("${service.host}") String host,
                                  @Value("${service.context-path:}") String contextPath) {
        super(host, contextPath);
    }

    @Override
    public Endpoint getEndpoint() {
        return Endpoint.ADD_BOOK;
    }

    @Override
    public String defaultBody() {
        return """
                {
                	"id": "5ff5f400-70b5-4f4e-af25-2260eaade029",
                	"title": "book title",
                	"publisher": "publisher name"
                }
                """;
    }
}
