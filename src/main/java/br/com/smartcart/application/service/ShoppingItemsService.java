package br.com.smartcart.application.service;

import br.com.smartcart.domain.valueobjects.ShoppingItemsVO;

public interface ShoppingItemsService {
    void saveShoppingItems(ShoppingItemsVO shoppingItemsVO, Long customerId);
}
