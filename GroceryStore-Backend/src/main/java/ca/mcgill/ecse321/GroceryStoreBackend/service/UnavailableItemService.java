package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.UnavailableItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.UnavailableItemDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Item;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.UnavailableItem;

@Service
public class UnavailableItemService {
	
	@Autowired
	UnavailableItemRepository unavailableItemRepository;
	
	@Transactional
	public UnavailableItem createUnavailableItem(String name, double price) {
				
		if(name==null || name.equals("")) throw new IllegalArgumentException("Item name cannot be blank");
		
		if(price<0) throw new IllegalArgumentException("Item price cannot be negative");
				
		nameIsValid(name);
				
		UnavailableItem item = new UnavailableItem();
		item.setName(name);
		item.setPrice(price);

		unavailableItemRepository.save(item);

		return item;
		
	}
	
	@Transactional
	public UnavailableItem updatePrice(String name, double newPrice) {
		
		if(name==null || name.equals("")) throw new IllegalArgumentException("Item name cannot be blank");
		
		if(newPrice<0) throw new IllegalArgumentException("Item price cannot be negative");
						
		UnavailableItem item = unavailableItemRepository.findByName(name);
		
		if(item==null)  throw new IllegalArgumentException("This item does not exist in the system");
		
		if(newPrice==unavailableItemRepository.findByName(name).getPrice()) throw new IllegalArgumentException("The price is the same");
		
		
		
		item.setPrice(newPrice);
		
		return item;
		
	}
	
	@Transactional
	public boolean deleteUnavailableItem(String name) {
		if(name==null || name.equals("")) throw new IllegalArgumentException("Item name cannot be blank");
		
		UnavailableItem item = unavailableItemRepository.findByName(name);
		
		if(item==null) throw new IllegalArgumentException("This item does not exist in the system");
		
		unavailableItemRepository.delete(item);
		
		return true;
	}
	
	@Transactional
	public UnavailableItem getUnavailableItem(String name) {
		
		if (unavailableItemRepository.findByName(name) == null)
			throw new IllegalArgumentException("This item does not exist in the system");
		
		return unavailableItemRepository.findByName(name);
	}
	
	
	private void nameIsValid(String name) {
		if(unavailableItemRepository.findByName(name)!=null) {
			throw new IllegalArgumentException("Item already in the system");
		}
	}

	@Transactional
	  public List<UnavailableItem> getAllUnavailableItems(){             
	      return toList(unavailableItemRepository.findAll());
	  }
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;

	}
}
