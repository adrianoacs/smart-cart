package br.com.smartcart.domain.valueobjects.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ShoppingItemsRsVO(String name, List<ProductRsVO> products) {
}
