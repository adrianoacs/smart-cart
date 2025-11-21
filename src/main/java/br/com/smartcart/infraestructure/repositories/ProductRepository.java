package br.com.smartcart.infraestructure.repositories;

import br.com.smartcart.domain.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByName(String name);

//    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')")
//    List<Product> findByString(String name);

    List<Product> findByNameContainingIgnoreCase(String name);
}
