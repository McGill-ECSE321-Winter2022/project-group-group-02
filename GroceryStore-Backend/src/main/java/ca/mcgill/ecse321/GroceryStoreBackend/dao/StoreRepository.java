package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Store;

public interface StoreRepository extends CrudRepository<Store, Long>{
	
	
	Store findStoreById(Long id);

	
}
