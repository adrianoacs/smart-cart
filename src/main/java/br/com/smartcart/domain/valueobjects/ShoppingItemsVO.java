package br.com.smartcart.domain.valueobjects;

import java.util.List;

public record ShoppingItemsVO(String name, List<ProductVO> marketItemList) {
}
