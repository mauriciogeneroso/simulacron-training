package com.generoso.ft.training.simulacron.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PrivateHealthResponse(String status) {

}
