package com.generoso.ft.training.simulacron.client.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum Endpoint {

    PRIVATE_INFO("/private/info", "GET"),
    PRIVATE_HEALTH_CHECK("/private/health", "GET"),
    PRIVATE_METRICS("/private/metrics", "GET"),
    ADD_BOOK("/books", "POST"),
    UPDATE_BOOK("/books", "PUT"),
    DELETE_BOOK("/books", "DELETE"),
    GET_BOOK_BY_ID("/books", "GET"),
    GET_ALL_BOOKS("/books", "GET");

    private final String path;
    private final String method;;
}