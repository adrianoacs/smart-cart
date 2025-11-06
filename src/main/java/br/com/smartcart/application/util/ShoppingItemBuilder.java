package br.com.smartcart.application.util;

import br.com.smartcart.domain.entities.ShoppingItems;
import br.com.smartcart.domain.valueobjects.request.ShoppingItemsRqVO;
import br.com.smartcart.domain.valueobjects.response.ProductRsVO;
import br.com.smartcart.domain.valueobjects.response.ShoppingItemsRsVO;

import java.time.LocalDateTime;


public class ShoppingItemBuilder {

    public static ShoppingItems toShoppingItems(ShoppingItemsRqVO shoppingItemsRqVO, Long customerId) {
        return ShoppingItems.builder()
                .name(shoppingItemsRqVO.name())
                .costumerId(customerId)
                .dtAdd(LocalDateTime.now())
                .build();
    }

    public static ShoppingItemsRsVO toShoppingItemsRsVO(ShoppingItems shoppingItems) {

        return ShoppingItemsRsVO.builder()
                .name(shoppingItems.getName())
                .products(shoppingItems.getProducts().stream()
                        .map(product -> ProductRsVO.builder()
                                .id(product.getProductId())
                                .name(product.getName())
                                .build()).toList())
                .build();
    }
}
