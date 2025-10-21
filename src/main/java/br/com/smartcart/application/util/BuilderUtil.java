package br.com.smartcart.application.util;

import br.com.smartcart.domain.entities.ShoppingItems;
import br.com.smartcart.domain.valueobjects.ShoppingItemsVO;

import java.time.LocalDateTime;


public class BuilderUtil {

    public static ShoppingItems toShoppingItems(ShoppingItemsVO shoppingItemsVO, Long customerId) {
        return ShoppingItems.builder()
                .name(shoppingItemsVO.name())
                .costumerId(customerId)
                .dtAdd(LocalDateTime.now())
                .build();
    }
}
