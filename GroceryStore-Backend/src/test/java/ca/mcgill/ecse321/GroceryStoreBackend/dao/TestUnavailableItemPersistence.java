package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
public class TestUnavailableItemPersistence {
	@Autowired
	  EntityManager entityManager;
	  
	  @Autowired
	  private UnavailableItemRepository unavailableItemRepository;
	  
	  @AfterEach
	  public void clearDatabase() {
	      unavailableItemRepository.deleteAll();
	  }
	  
	  @Test
	  public void testPersistAndLoadCustomer() {
	      String name = "magazine";
	      double price = 11.0;
	      UnavailableItem unavailableItem=new UnavailableItem(name, price);
	      
	      unavailableItemRepository.save(unavailableItem);

	      unavailableItem = null;

	      unavailableItem = unavailableItemRepository.findUnavailableItemByName(name);
	      assertNotNull(unavailableItem);
	      assertEquals(name, unavailableItem.getName());
}
