package ca.mcgill.ecse321.GroceryStoreBackend.service;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;

public class ShoppableItemService {
	
	public ShoppableItemService() {
		
	}
	
	ShoppableItemRepository shoppableItemRepository;
	
	public ShoppableItem createShoppableItem(String name, double price, int quantityAvailable) {
		
		if(name==null || name.equals("")) throw new IllegalArgumentException("Item name cannot be blank");
		
		if(price<0) throw new IllegalArgumentException("Item price cannot be negative");
		
		nameIsValid(name);
		
		if(quantityAvailable<0) throw new IllegalArgumentException("Item quantity cannot be negative");
		
		ShoppableItem item = new ShoppableItem();
		item.setName(name);
		item.setPrice(price);
		item.setQuantityAvailable(quantityAvailable);

		shoppableItemRepository.save(item);

		return item;
		
	}
	
	public ShoppableItem updateShoppableItem(String name, double newPrice, int newQuantityAvailable) {
		
		if(newPrice<0) throw new IllegalArgumentException("Item price cannot be negative");
				
		if(newQuantityAvailable<0) throw new IllegalArgumentException("Item quantity cannot be negative");
		
		ShoppableItem item = shoppableItemRepository.findByName(name);
		
		if(item==null)  throw new IllegalArgumentException("This item does not exist in the system");
		
		item.setPrice(newPrice);
		item.setQuantityAvailable(newQuantityAvailable);
		return item;
	}
	
	public boolean deleteShoppableItem(String name) {
		ShoppableItem item = shoppableItemRepository.findByName(name);
		
		if(item==null) throw new IllegalArgumentException("This item does not exist in the system");
		
		item.delete();
		
		return true;
	}
	
	public ShoppableItem getShoppableItem(String name) {
		return shoppableItemRepository.findByName(name);
	}
	
	private void nameIsValid(String name) {
		if(shoppableItemRepository.findByName(name)!=null) {
			throw new IllegalArgumentException("Item name already exist");
		}
	}
}




