package br.com.smartcart.application.util;

import br.com.smartcart.domain.entities.Product;
import br.com.smartcart.domain.entities.ShoppingItems;
import br.com.smartcart.domain.entities.ShoppingItemsProduct;
import br.com.smartcart.domain.entities.ShoppingItemsProductId;
import br.com.smartcart.domain.valueobjects.request.ShoppingItemsRqVO;
import br.com.smartcart.domain.valueobjects.response.ProductRsVO;
import br.com.smartcart.domain.valueobjects.response.ShoppingItemsRsVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;


public class ShoppingItemBuilder {

    public static ShoppingItems toShoppingItems(ShoppingItemsRqVO shoppingItemsRqVO, Long customerId) {
        return ShoppingItems.builder()
                .name(shoppingItemsRqVO.name())
                .costumerId(customerId)
                .dtAdd(LocalDateTime.now())
                .build();
    }

    public static List<ShoppingItemsRsVO> toShoppingItemsRsVOList(List<ShoppingItems> shoppingItems) {
        return shoppingItems.stream().map(item ->
                ShoppingItemsRsVO.builder()
                        .id(item.getShoppingItemsId())
                        .name(item.getName())
                        .build()).toList();
    }

    public static ShoppingItemsRsVO toShoppingItemsRsVO(ShoppingItems shoppingItems) {

        return ShoppingItemsRsVO.builder()
                .id(shoppingItems.getShoppingItemsId())
                .name(shoppingItems.getName())
                .marketItemList(shoppingItems.getShoppingItemsProducts().stream()
                        .map(shoppingItemsProduct -> ProductRsVO.builder()
                                .id(shoppingItemsProduct.getProduct().getProductId())
                                .name(shoppingItemsProduct.getProduct().getName())
                                .position(shoppingItemsProduct.getPosition())
                                .build()).toList())
                .build();
    }

    public static List<ShoppingItemsProduct> getShoppingItemsProducts(ArrayList<Product> products, ShoppingItems shoppingItems) {
        return IntStream.range(0, products.size())
                .mapToObj(i -> {
                    Product product = products.get(i);
                    return ShoppingItemsProduct.builder()
                            .id(ShoppingItemsProductId.builder()
                                    .shoppingItemsId(shoppingItems.getShoppingItemsId())
                                    .productId(product.getProductId())
                                    .build())
                            .shoppingItems(shoppingItems)
                            .product(product)
                            .position(i + 1)
                            .build();
                }).toList();
    }
}
