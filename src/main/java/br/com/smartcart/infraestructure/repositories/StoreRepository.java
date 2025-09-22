package br.com.smartcart.infraestructure.repositories;

import br.com.smartcart.domain.entities.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends CrudRepository<Store, Long> {

    List<Store> findByName(String name);
}
