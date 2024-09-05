package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
public record ExchangeTokenRequest(@JsonProperty("public_token") String publicToken) {
}
