package br.com.smartcart.application.service;

import br.com.smartcart.domain.valueobjects.ShoppingItemsVO;

public interface ShoppingItemsService {

    void save(ShoppingItemsVO shoppingItemsVO, Long customerId);

    void delete(Long shoppingItemsId);

}
