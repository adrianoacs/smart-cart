package br.com.smartcart.api.rest.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ShoppingItemsRq(Long id, String name, List<ProductRq> marketItemList) {
}
