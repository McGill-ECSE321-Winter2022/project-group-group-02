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
public class TestOrderItemPersistence {

	@Autowired
	EntityManager entityManager;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ShoppableItemRepository shoppableItemRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderRepository orderRepository;

	@AfterEach
	public void clearDatabase() {

		orderRepository.deleteAll();
		orderItemRepository.deleteAll();
		shoppableItemRepository.deleteAll();
		customerRepository.deleteAll();

	}

	@Test
	public void testPersistAndLoadOrderItem() {
		
		// Creation of a ShoppableItem instance with test attributes
		String itemName = "Milk";
		double price = 4.65;
		int quantityAvailable = 10;
		ShoppableItem shoppableItem = new ShoppableItem();
		shoppableItem.setName(itemName);
		shoppableItem.setPrice(price);
		shoppableItem.setQuantityAvailable(quantityAvailable);
		
		// Save the created ShoppableItem instance
		shoppableItemRepository.save(shoppableItem);

		// Creation of an OrderItem instance with test attributes
		Long orderItemId = (long) 1234;
		int quantityWanted = 2;
		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(quantityWanted);
		orderItem.setId(orderItemId);

		// Set the ShoppableItem attribute of orderItem, because of the many-to-one association
		orderItem.setItem(shoppableItem);

		// Save the created OrderItem instance
		orderItemRepository.save(orderItem);

		// Set the variable to null, and then try retrieving the saved instance using its id
		orderItem = null;
		orderItem = orderItemRepository.findOrderItemById(orderItemId);
		
		// Determine whether the instance is null and if the attribute deliveryFee matches.
		assertNotNull(orderItem);
		assertEquals(orderItemId, orderItem.getId());
	}

}
