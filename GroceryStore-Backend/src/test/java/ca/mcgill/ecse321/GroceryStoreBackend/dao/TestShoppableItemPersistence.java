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
		
		// Creation of a ShoppableItem instance with test attributes
		String name = "Bread";
		double price = 5.0;
		Integer quantityAvailable = 4;
		ShoppableItem shoppableItem = new ShoppableItem(name, price, quantityAvailable);

		// Save the created ShoppableItem instance
		shoppableItemRepository.save(shoppableItem);

		// Set the variable to null, and then try retrieving the saved instance using its name
		shoppableItem = null;
		shoppableItem = shoppableItemRepository.findByName(name);
		
		// Determine whether the instance is null and if the attribute quantityAvailable matches.
		assertNotNull(shoppableItem);
		assertEquals(quantityAvailable, shoppableItem.getQuantityAvailable());
	}
}
