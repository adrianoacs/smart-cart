package br.com.smartcart.infraestructure.repositories;

import br.com.smartcart.domain.ProductPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPriceRepository extends CrudRepository<ProductPrice, Long> {
}
