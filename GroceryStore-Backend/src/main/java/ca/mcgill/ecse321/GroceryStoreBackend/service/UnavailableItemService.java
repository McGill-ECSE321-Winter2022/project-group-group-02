package ca.mcgill.ecse321.GroceryStoreBackend.service;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.UnavailableItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Item;
import ca.mcgill.ecse321.GroceryStoreBackend.model.UnavailableItem;

public class UnavailableItemService {
	
	UnavailableItemRepository unavailableItemRepository;
	
	public UnavailableItem createItem(String name, double price) {
		
		UnavailableItemRepository unavailableItemRepository;
		
		if(name==null || name.equals("")) throw new IllegalArgumentException("Item name cannot be blank");
		
		if(price<0) throw new IllegalArgumentException("Item price cannot be negative");
		
		nameIsValid(name);
				
		UnavailableItem item = new UnavailableItem();
		item.setName(name);
		item.setPrice(price);

		unavailableItemRepository.save(item);

		return item;
		
	}
	
	public UnavailableItem updateUnavailableItem(String name, double newPrice) {
		
		if(newPrice<0) throw new IllegalArgumentException("Item price cannot be negative");
						
		UnavailableItem item = unavailableItemRepository.findByName(name);
		
		if(item==null)  throw new IllegalArgumentException("This item does not exist in the system");
		
		item.setPrice(newPrice);
		
		
	}
	
	public boolean deleteUnavailableItem(String name) {
		UnavailableItem item = unavailableItemRepository.findByName(name);
		
		if(item==null) throw new IllegalArgumentException("This item does not exist in the system");
		
		item.delete();
		
		return true;
	}
	
	public UnavailableItem getUnavailableItem(String name) {
		return unavailableItemRepository.findByName(name);
	}
	
	
	private void nameIsValid(String name) {
		if(unavailableItemRepository.findByName(name)!=null) {
			throw new IllegalArgumentException("Item name already exist");
		}
	}
}
