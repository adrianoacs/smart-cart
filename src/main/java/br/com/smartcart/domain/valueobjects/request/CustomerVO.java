package br.com.smartcart.domain.valueobjects.request;

public record CustomerVO(Long customerId, String name, String externalId, String email) {
}

