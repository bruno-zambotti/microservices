package br.com.zambotti.inventory.repository;

import br.com.zambotti.inventory.model.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Integer>{
    Optional<Inventory> findByProductId(Integer id);
}
