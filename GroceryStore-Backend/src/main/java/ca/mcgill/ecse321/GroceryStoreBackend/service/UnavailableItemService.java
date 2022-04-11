package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.UnavailableItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.UnavailableItem;

@Service
public class UnavailableItemService {

	@Autowired
	UnavailableItemRepository unavailableItemRepository;
	
	/**
	 * Creates an unavailable item in the system
	 * 
	 * @param name
	 * @param price
	 * @return the created unavailable item
	 * @throws IllegalArgumentException if parameters are invalid, or if the item already exists in the system
	 * 
	 * @author Ralph Nassar
	 */
	
	@Transactional
	public UnavailableItem createUnavailableItem(String name, double price) {
		//if name is empty
		if (name == null || name.equals(""))
			throw new IllegalArgumentException("Item name cannot be blank");
		//negative price
		if (price < 0)
			throw new IllegalArgumentException("Item price cannot be negative");
		//item already in the system
		nameIsValid(name);
		
		//create the item if parameters are successful
		UnavailableItem item = new UnavailableItem();
		item.setName(name);
		item.setPrice(price);
		
		// and add it to the system
		unavailableItemRepository.save(item);

		return item;

	}
	
	/**
	 * Updates the price of an unavailable item
	 * 
	 * @param name
	 * @param newPrice
	 * @return the updated unavailable item
	 * @throws IllegalArgumentException if parameters are invalid, or if the item does not exist in the system
	 * 
	 * @author Ralph Nassar
	 */
	
	@Transactional
	public UnavailableItem updatePrice(String name, double newPrice) {
		//empty name
		if (name == null || name.equals(""))
			throw new IllegalArgumentException("Item name cannot be blank");
		// negative price
		if (newPrice < 0)
			throw new IllegalArgumentException("Item price cannot be negative");

		UnavailableItem item = unavailableItemRepository.findByName(name);
		//item not in the system
		if (item == null)
			throw new IllegalArgumentException("This item does not exist in the system");
		//New price same as old price
		if (newPrice == unavailableItemRepository.findByName(name).getPrice())
			throw new IllegalArgumentException("The price is the same");
		//Update the price if parameters are valid
		item.setPrice(newPrice);

		return item;

	}
	
	/**
	 * Deletes an unavailable item from the system
	 * 
	 * @param name
	 * @return true if the item was successfully deleted
	 * @throws IllegalArgumentException if parameters are invalid, or if the item does not exist in the system
	 * 
	 * @author Ralph Nassar
	 */
	
	@Transactional
	public boolean deleteUnavailableItem(String name) {
		//Empty name
		if (name == null || name.equals(""))
			throw new IllegalArgumentException("Item name cannot be blank");

		UnavailableItem item = unavailableItemRepository.findByName(name);
		//Item not found in the system
		if (item == null)
			throw new IllegalArgumentException("This item does not exist in the system");

		unavailableItemRepository.delete(item);

		return true;
	}
	
	/**
	 * Return an unavailable item in the system
	 * 
	 * @param name
	 * @return an unavailable item
	 * @throws IllegalArgumentException if the item does not exist in the system
	 * 
	 * @author Ralph Nassar
	 */

	@Transactional
	public UnavailableItem getUnavailableItem(String name) {

		if (unavailableItemRepository.findByName(name) == null)
			throw new IllegalArgumentException("This item does not exist in the system");

		return unavailableItemRepository.findByName(name);
	}
	
	/**
	 * Helper method that throws exception if the item is already in the system
	 * @param name
	 * 
	 * @author Ralph Nassar
	 */
	
	private void nameIsValid(String name) {
		if (unavailableItemRepository.findByName(name) != null) {
			throw new IllegalArgumentException("Item already in the system");
		}
	}
	
	/**
	 * Return a list containing all the unavailable items in the system
	 * 
	 * @return a list of unavailable items
	 * 
	 * @author Ralph Nassar
	 */
	
	@Transactional
	public List<UnavailableItem> getAllUnavailableItems() {
		return toList(unavailableItemRepository.findAll());
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
