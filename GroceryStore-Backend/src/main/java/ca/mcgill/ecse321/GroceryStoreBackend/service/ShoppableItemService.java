package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;

@Service
public class ShoppableItemService {
	
	public ShoppableItemService() {
		
	}
	
	@Autowired
	ShoppableItemRepository shoppableItemRepository;
	
	@Transactional
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
	
	@Transactional
	public ShoppableItem updatePrice(String name, double newPrice) {
		
		if(newPrice<0) throw new IllegalArgumentException("Item price cannot be negative");
				
		ShoppableItem item = shoppableItemRepository.findByName(name);
		
		if(item==null)  throw new IllegalArgumentException("This item does not exist in the system");
		
		item.setPrice(newPrice);
		
		return item;
	}
	
	@Transactional
	public ShoppableItem replenishInventory(String name, int newQuantityAvailable) {
		
		if(newQuantityAvailable<0) throw new IllegalArgumentException("The quantity cannot be negative");
				
		ShoppableItem item = shoppableItemRepository.findByName(name);
		
		if(item==null)  throw new IllegalArgumentException("This item does not exist in the system");
		
		item.setQuantityAvailable(newQuantityAvailable);
		
		return item;
	}
	
	@Transactional
	public boolean deleteShoppableItem(String name) {
		ShoppableItem item = shoppableItemRepository.findByName(name);
		
		if(item==null) throw new IllegalArgumentException("This item does not exist in the system");
		
		item.delete();
		
		return true;
	}
	
	@Transactional
	public ShoppableItem getShoppableItem(String name) {
		return shoppableItemRepository.findByName(name);
	}
	
	@Transactional
	  public List<ShoppableItem> getAllShoppableItems(){             
	      return toList(shoppableItemRepository.findAll());
	  }
	
	private void nameIsValid(String name) {
		if(shoppableItemRepository.findByName(name)!=null) {
			throw new IllegalArgumentException("Item name already exist");
		}
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}
}




