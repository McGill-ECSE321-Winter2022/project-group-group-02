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
	public void testPersistAndLoadUnavailableItem() {
		
		// Creation of an UnavailableItem instance with test attributes
		String name = "magazine";
		double price = 11.0;
		UnavailableItem unavailableItem = new UnavailableItem(name, price);

		// Save the created instance
		unavailableItemRepository.save(unavailableItem);

		// Set the variable to null, and then try retrieving the saved instance using its name
		unavailableItem = null; 
		unavailableItem = unavailableItemRepository.findByName(name);
		
		// Determine whether the instance is null and if the attribute name match.
		assertNotNull(unavailableItem);
		assertEquals(name, unavailableItem.getName());
	}
}