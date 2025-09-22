package br.com.smartcart.infraestructure.repositories;

import br.com.smartcart.domain.entities.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    List<Invoice> findByNumber(String number);
}
