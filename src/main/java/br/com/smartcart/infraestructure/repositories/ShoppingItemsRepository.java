package br.com.smartcart.infraestructure.repositories;

import br.com.smartcart.domain.entities.ShoppingItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingItemsRepository extends CrudRepository<ShoppingItems, Long> {

    List<ShoppingItems> findAllByCostumerId(Long costumerId);

}
