package br.com.smartcart.domain.valueobjects.request;

import java.util.List;

public record ShoppingItemsRqVO(Long id, String name, List<ProductRqVO> marketItemList) {
}
