package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.UnavailableItem;

public interface UnavailableItemRepository extends CrudRepository<UnavailableItem, String>{
	
	UnavailableItem findUnavailableItemByName(String name);
	
}
