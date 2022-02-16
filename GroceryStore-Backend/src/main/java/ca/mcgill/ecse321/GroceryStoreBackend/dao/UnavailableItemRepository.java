package ca.mcgill.ecse321.GroceryStoreBackend.dao;

public interface UnavailableItemRepository extends CrudRepository<UnavailableItem, String>{
	
	UnavailableItem findUnavailableItemByName(String name);
	
}
