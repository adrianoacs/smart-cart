package br.com.smartcart.application.service.impl;

import br.com.smartcart.domain.entities.ShoppingItems;
import br.com.smartcart.domain.entities.ShoppingItemsProduct;
import br.com.smartcart.domain.entities.ShoppingItemsProductId;
import br.com.smartcart.domain.exception.ShoppingItemNotFoundException;
import br.com.smartcart.domain.valueobjects.response.ShoppingItemsRsVO;
import br.com.smartcart.application.service.ShoppingItemsService;
import br.com.smartcart.application.util.ShoppingItemBuilder;
import br.com.smartcart.domain.entities.Product;
import br.com.smartcart.domain.valueobjects.request.ProductRqVO;
import br.com.smartcart.domain.valueobjects.request.ShoppingItemsRqVO;
import br.com.smartcart.infraestructure.repositories.ProductRepository;
import br.com.smartcart.infraestructure.repositories.ShoppingItemsProductRepository;
import br.com.smartcart.infraestructure.repositories.ShoppingItemsRepository;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class ShoppingItemsServiceImpl implements ShoppingItemsService {

    private final ProductRepository productRepository;
    private final ShoppingItemsRepository shoppingItemsRepository;
    private final ShoppingItemsProductRepository shoppingItemsProductRepository;

    public ShoppingItemsServiceImpl(ProductRepository productRepository,
                                    ShoppingItemsRepository shoppingItemsRepository,
                                    ShoppingItemsProductRepository shoppingItemsProductRepository) {
        this.productRepository = productRepository;
        this.shoppingItemsRepository = shoppingItemsRepository;
        this.shoppingItemsProductRepository = shoppingItemsProductRepository;
    }

    @Override
    public void save(ShoppingItemsRqVO shoppingItemsRqVO, Long customerId) {


        var shoppingItems = ShoppingItemBuilder.toShoppingItems(shoppingItemsRqVO, customerId);
        shoppingItemsRepository.save(shoppingItems);

        // list with IDs of products that already exist
        var existingProductIds = shoppingItemsRqVO.marketItemList().stream().map(ProductRqVO::id)
                .filter(Objects::nonNull)
                .toList();
        // list with new products (ids == null)
        var newProducts = shoppingItemsRqVO.marketItemList().stream().filter(item -> item.id() == null)
                .map(item -> Product.builder().name(item.name()).build())
                .toList();

        // get managed entities on the database
        var existingProducts = (Collection<Product>) productRepository.findAllById(existingProductIds);
        // save and get new products managed
        var savedNewProducts = (Collection<Product>) productRepository.saveAll(newProducts);
        var allProducts = new ArrayList<Product>();
        allProducts.addAll(existingProducts);
        allProducts.addAll(savedNewProducts);

        var shoppingItemsProducts = ShoppingItemBuilder.getShoppingItemsProducts(allProducts, shoppingItems);
        shoppingItemsProductRepository.saveAll(shoppingItemsProducts);

    }



    @Override
    public void delete(Long shoppingItemsId) {
        shoppingItemsRepository.findById(shoppingItemsId).ifPresent(shop -> {
            shop.setBlocked(true);
            shoppingItemsRepository.save(shop);
        });
    }

    @Override
    public ShoppingItemsRsVO find(Long shoppingItemsId) {
        var shoppingItems = shoppingItemsRepository.findById(shoppingItemsId)
                .orElseThrow(() -> new ShoppingItemNotFoundException(shoppingItemsId));
        return ShoppingItemBuilder.toShoppingItemsRsVO(shoppingItems);
    }

}
