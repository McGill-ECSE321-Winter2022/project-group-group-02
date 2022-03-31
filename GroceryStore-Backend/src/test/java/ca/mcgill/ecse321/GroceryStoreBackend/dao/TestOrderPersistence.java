package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOrderPersistence {

	@Autowired
	EntityManager entityManager;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ShoppableItemRepository shoppableItemRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@AfterEach
	public void clearDatabase() {

		orderRepository.deleteAll();
		orderItemRepository.deleteAll();
		shoppableItemRepository.deleteAll();
		customerRepository.deleteAll();

	}

	@Test
	public void testPersistAndLoadOrder() {

		
		// Creation of a Customer instance with test attributes
		String name = "testCustomer";
		String email = "testCustomer@mail.com";
		String password = "testPassword";
		String address = "town";
		Customer customer = new Customer();
		customer.setName(name);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setAddress(address);

		// Save the created DailySchedule instance
		customerRepository.save(customer);

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
		//Long orderItemId = (long) 1234;
		int quantityWanted = 2;
		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(quantityWanted);
		orderItem.setItem(shoppableItem);
		//orderItem.setId(orderItemId);
		
		// Save the created OrderItem instance
		OrderItem newItem = orderItemRepository.save(orderItem);

		// Creation of an Order instance with test attributes
		//Long orderId = (long) 1234;
		OrderType orderType = OrderType.Delivery;
		OrderStatus orderStatus = OrderStatus.Confirmed;
		Date date = new Date(0);
		Time time = new Time(0);
		Order order = new Order();
		order.setOrderType(orderType);
		order.setOrderStatus(orderStatus);
		order.setDate(date);
		order.setTime(time);
		//order.setId(orderId);

		// Set the Customer & OrderItem attribute of order, because of the many-to-one and one-to-many associations
		order.setCustomer(customer);
		order.addOrderItem(newItem);

		// Save the created Order instance
		Order newOrder = orderRepository.save(order);

		// Set the variable to null, and then try retrieving the saved instance using its id
		order = null;
		order = orderRepository.findOrderById(newOrder.getId());
		
		// Determine whether the instance is null, if the attribute orderType matches and if the reference to customer matches.
		assertNotNull(order);
		assertEquals(orderType, order.getOrderType());
		assertEquals(customer.getEmail(), order.getCustomer().getEmail()); // Checking by email because it's the customer's id
	}

}
