package br.com.smartcart.infraestructure.repositories;

import br.com.smartcart.domain.entities.ProductPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPriceRepository extends CrudRepository<ProductPrice, Long> {

    List<ProductPrice> findByProductIdAndInvoiceId(Long productId, Long invoiceId);
}
