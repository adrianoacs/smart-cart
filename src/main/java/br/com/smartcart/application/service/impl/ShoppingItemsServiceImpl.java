package br.com.smartcart.application.service.impl;

import br.com.smartcart.application.service.ShoppingItemsService;
import br.com.smartcart.application.util.BuilderUtil;
import br.com.smartcart.domain.entities.Product;
import br.com.smartcart.domain.valueobjects.ProductVO;
import br.com.smartcart.domain.valueobjects.ShoppingItemsVO;
import br.com.smartcart.infraestructure.repositories.InvoiceRepository;
import br.com.smartcart.infraestructure.repositories.ProductPriceRepository;
import br.com.smartcart.infraestructure.repositories.ProductRepository;
import br.com.smartcart.infraestructure.repositories.ShoppingItemsRepository;
import br.com.smartcart.infraestructure.repositories.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Service
public class ShoppingItemsServiceImpl implements ShoppingItemsService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final ProductPriceRepository productPriceRepository;
    private final InvoiceRepository invoiceRepository;
    private final ShoppingItemsRepository shoppingItemsRepository;

    public ShoppingItemsServiceImpl(ProductRepository productRepository,
                                    StoreRepository storeRepository,
                                    ProductPriceRepository productPriceRepository,
                                    InvoiceRepository invoiceRepository,
                                    ShoppingItemsRepository shoppingItemsRepository) {
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.productPriceRepository = productPriceRepository;
        this.invoiceRepository = invoiceRepository;
        this.shoppingItemsRepository = shoppingItemsRepository;
    }

    @Override
    public void saveShoppingItems(ShoppingItemsVO shoppingItemsVO, Long customerId) {


        var shoppingItems = BuilderUtil.toShoppingItems(shoppingItemsVO, customerId);

        // list with IDs of products that already exist
        var existingProductIds = shoppingItemsVO.marketItemList().stream().map(ProductVO::id)
                .filter(Objects::nonNull)
                .toList();
        // list with new products (ids == null)
        var newProducts = shoppingItemsVO.marketItemList().stream().filter(item -> item.id() == null)
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

}
