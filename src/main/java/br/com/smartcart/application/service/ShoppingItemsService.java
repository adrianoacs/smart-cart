package br.com.smartcart.application.service;

import br.com.smartcart.domain.valueobjects.request.ShoppingItemsRqVO;
import br.com.smartcart.domain.valueobjects.response.ShoppingItemsRsVO;

public interface ShoppingItemsService {

    void save(ShoppingItemsRqVO shoppingItemsRqVO, Long customerId);

    void update(ShoppingItemsRqVO shoppingItemsRqVO, Long customerId);

    void delete(Long shoppingItemsId);

    ShoppingItemsRsVO find(Long shoppingItemsId);

}
