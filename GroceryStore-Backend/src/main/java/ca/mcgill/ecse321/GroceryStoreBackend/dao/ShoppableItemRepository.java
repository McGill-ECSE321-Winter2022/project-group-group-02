package ca.mcgill.ecse321.GroceryStoreBackend.dao;

public interface ShoppableItemRepository extends CrudRepository<ShoppableItem, String>{
	
	ShoppableItem findShoppableItemByName(String name);
	
}
