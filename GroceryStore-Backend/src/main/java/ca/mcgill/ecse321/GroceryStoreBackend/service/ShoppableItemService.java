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
	
	/**
	 * Creates a shoppable item in the system
	 * 
	 * @param name
	 * @param price
	 * @param quantityAvailable
	 * @return the created shoppable item
	 * @throws IllegalArgumentException if parameters are invalid, or if the item already exists in the system
	 * 
	 * @author Ralph Nassar
	 */
	
	@Transactional
	public ShoppableItem createShoppableItem(String name, double price, int quantityAvailable) {
		
		// if the entered name is empty
		if (name == null || name.equals(""))
			throw new IllegalArgumentException("Item name cannot be blank");
		
		// If the price is negative
		if (price < 0)
			throw new IllegalArgumentException("Item price cannot be negative");
		
		// If the item already exists in the system
		nameIsValid(name);
		
		// If the quantity available is negative
		if (quantityAvailable < 0)
			throw new IllegalArgumentException("Item quantity cannot be negative");
		
		// Create an item if informations are valid
		ShoppableItem item = new ShoppableItem();
		item.setName(name);
		item.setPrice(price);
		item.setQuantityAvailable(quantityAvailable);

		shoppableItemRepository.save(item);

		return item;

	}
	
	/**
	 * Updates the price of a shoppable item
	 * 
	 * @param name
	 * @param newPrice
	 * @return the updated shoppable item
	 * @throws IllegalArgumentException if parameters are invalid, or if the item does not exist in the system
	 * 
	 * @author Ralph Nassar
	 */

	@Transactional
	public ShoppableItem updatePrice(String name, double newPrice) {
		
		// if the entered name is empty
		if (name == null || name.equals(""))
			throw new IllegalArgumentException("Item name cannot be blank");
		// Negative price
		if (newPrice < 0)
			throw new IllegalArgumentException("Item price cannot be negative");

		ShoppableItem item = shoppableItemRepository.findByName(name);
		// Item not found in the system
		if (item == null)
			throw new IllegalArgumentException("This item does not exist in the system");
		//
		item.setPrice(newPrice);

		return item;
	}
	
	/**
	 * Updates the inventory of a shoppable item
	 * 
	 * @param name
	 * @param newQuantityAvailable
	 * @return the updated shoppable item
	 * @throws IllegalArgumentException if parameters are invalid, or if the item does not exist in the system
	 * 
	 * @author Ralph Nassar
	 */
	
	@Transactional
	public ShoppableItem updateInventory(String name, int newQuantityAvailable) {
		//Empty name
		if (name == null || name.equals(""))
			throw new IllegalArgumentException("Item name cannot be blank");
		//Negative quantity
		if (newQuantityAvailable < 0)
			throw new IllegalArgumentException("The quantity cannot be negative");

		ShoppableItem item = shoppableItemRepository.findByName(name);
		//Item not in the system
		if (item == null)
			throw new IllegalArgumentException("This item does not exist in the system");
		//quantity the same as before
		if (newQuantityAvailable == item.getQuantityAvailable())
			throw new IllegalArgumentException("The quantity is the same");
		
		//if quantity is the same as before
		item.setQuantityAvailable(newQuantityAvailable);

		return item;
	}
	
	/**
	 * Deletes a shoppable item from the system
	 * 
	 * @param name
	 * @return true if the item was successfully deleted
	 * @throws IllegalArgumentException if parameters are invalid, or if the item does not exist in the system
	 * 
	 * @author Ralph Nassar
	 */
	
	@Transactional
	public boolean deleteShoppableItem(String name) {
		//if the name is empty
		if (name == null || name.equals(""))
			throw new IllegalArgumentException("Item name cannot be blank");

		ShoppableItem item = shoppableItemRepository.findByName(name);
		//Item not in the system
		if (item == null)
			throw new IllegalArgumentException("This item does not exist in the system");
		
		//If the name is valid, so delete
		shoppableItemRepository.delete(item);
		item.delete();

		return true;
	}
	
	/**
	 * Return a shoppable item in the system
	 * 
	 * @param name
	 * @return a shoppable item
	 * @throws IllegalArgumentException if the item does not exist in the system
	 * 
	 * @author Ralph Nassar
	 */

	@Transactional
	public ShoppableItem getShoppableItem(String name) {

		if (shoppableItemRepository.findByName(name) == null)
			throw new IllegalArgumentException("This item does not exist in the system");

		return shoppableItemRepository.findByName(name);
	}
	
	/**
	 * Return a list containing all the shoppable items in the system
	 * 
	 * @return a list of shoppable items
	 * 
	 * @author Ralph Nassar
	 */
	
	@Transactional
	public List<ShoppableItem> getAllShoppableItems() {
		return toList(shoppableItemRepository.findAll());
	}
	
	/**
	 * Helper method that throws exception if the item is already in the system
	 * @param name
	 * 
	 * @author Ralph Nassar
	 */
	
	private void nameIsValid(String name) {
		if (shoppableItemRepository.findByName(name) != null) {
			throw new IllegalArgumentException("Item already in the system");
		}
	}
	
	/**
	 * Helper method to return a list of iterable object
	 * @param <T>
	 * @param iterable
	 * @return resultList
	 */
	
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}
}
