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

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestShoppableItemPersistence {
	
      @Autowired
	  EntityManager entityManager;
	  
	  @Autowired
	  private ShoppableItemRepository shoppableItemRepository;
	  
	  @AfterEach
	  public void clearDatabase() {
	      shoppableItemRepository.deleteAll();
	  }
	  
	  @Test
	  public void testPersistAndLoadShoppableItem() {
	      String name = "Bread";
	      double price = 5.0;
	      Integer quantityAvailable=4;
	      ShoppableItem shoppableItem = new ShoppableItem(name, price, quantityAvailable);
	      
	      shoppableItemRepository.save(shoppableItem);

	      shoppableItem = null;

	      shoppableItem = shoppableItemRepository.findByName(name);
	      assertNotNull(shoppableItem);
	      assertEquals(name, shoppableItem.getName());
	  }
}
