package br.com.smartcart.infraestructure.repositories;

import br.com.smartcart.domain.entities.ShoppingItemsProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingItemsProductRepository extends CrudRepository<ShoppingItemsProduct, Long> {

}
