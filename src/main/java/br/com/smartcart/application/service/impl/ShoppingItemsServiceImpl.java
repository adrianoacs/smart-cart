package br.com.smartcart.application.service.impl;

import br.com.smartcart.domain.exception.ShoppingItemNotFoundException;
import br.com.smartcart.domain.valueobjects.response.ShoppingItemsRsVO;
import br.com.smartcart.application.service.ShoppingItemsService;
import br.com.smartcart.application.util.ShoppingItemBuilder;
import br.com.smartcart.domain.entities.Product;
import br.com.smartcart.domain.valueobjects.request.ProductRqVO;
import br.com.smartcart.domain.valueobjects.request.ShoppingItemsRqVO;
import br.com.smartcart.infraestructure.repositories.ProductRepository;
import br.com.smartcart.infraestructure.repositories.ShoppingItemsRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class ShoppingItemsServiceImpl implements ShoppingItemsService {

    private final ProductRepository productRepository;
    private final ShoppingItemsRepository shoppingItemsRepository;

    public ShoppingItemsServiceImpl(ProductRepository productRepository,
                                    ShoppingItemsRepository shoppingItemsRepository) {
        this.productRepository = productRepository;
        this.shoppingItemsRepository = shoppingItemsRepository;
    }

    @Override
    public void save(ShoppingItemsRqVO shoppingItemsRqVO, Long customerId) {


        var shoppingItems = ShoppingItemBuilder.toShoppingItems(shoppingItemsRqVO, customerId);

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

        if(shoppingItems.getProducts() == null)
        {
            shoppingItems.setProducts(new ArrayList<>());
        }
        // join existing products with new products into the entity 'shoppingItems' and save all
        shoppingItems.getProducts().addAll(savedNewProducts);
        shoppingItems.getProducts().addAll(existingProducts);

        shoppingItemsRepository.save(shoppingItems);
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
