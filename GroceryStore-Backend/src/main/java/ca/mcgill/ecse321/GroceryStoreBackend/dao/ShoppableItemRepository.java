package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;

public interface ShoppableItemRepository extends CrudRepository<ShoppableItem, String> {

	ShoppableItem findByName(String name);

}
