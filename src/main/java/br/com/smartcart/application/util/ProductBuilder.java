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
import java.util.stream.IntStream;


public class ProductBuilder {

    public static ProductRsVO toProductRsVO(Product product){
        return ProductRsVO.builder()
                .id(product.getProductId())
                .name(product.getName())
                .build();
    }

    public static List<ProductRsVO> toProductRsVOList(List<Product> productList){
        return productList.stream().map(ProductBuilder::toProductRsVO).toList();
    }
}
