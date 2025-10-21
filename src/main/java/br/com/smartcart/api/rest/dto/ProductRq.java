package br.com.smartcart.api.rest.dto;

import lombok.Builder;

@Builder
public record ProductRq(Long id, String name) {
}
