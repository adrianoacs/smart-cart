package br.com.smartcart.domain.valueobjects.response;

import lombok.Builder;

@Builder
public record ProductRsVO(Long id, String name, Integer position) {
}
