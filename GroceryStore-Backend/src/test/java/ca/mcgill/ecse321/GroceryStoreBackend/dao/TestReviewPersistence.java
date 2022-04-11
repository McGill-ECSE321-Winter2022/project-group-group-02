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
import ca.mcgill.ecse321.GroceryStoreBackend.model.Review.Rating;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestReviewPersistence {

	@Autowired
	EntityManager entityManager;

	@Autowired
	private ReviewRepository reviewRepository;

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
		reviewRepository.deleteAll();
		orderRepository.deleteAll();
		orderItemRepository.deleteAll();
		customerRepository.deleteAll();
		shoppableItemRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadReview() {

		// Creation of a Customer instance with test attributes
		String name = "testCustomer";
		String email = "testCustomer@mail.com";
		String password = "testPassword";
		String address = "town";
		Customer customer = new Customer();
		customer.setAddress(address);
		customer.setEmail(email);
		customer.setPassword(password);
		customer.setName(name);

		// Save the created Customer instance
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
		int quantityWanted = 2;
		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(quantityWanted);
		orderItem.setItem(shoppableItem);

		// Save the created OrderItem instance
		OrderItem newItem = orderItemRepository.save(orderItem);

		// Creation of an Order instance with test attributes
		OrderType orderType = OrderType.Delivery;
		OrderStatus orderStatus = OrderStatus.Confirmed;
		Date date = new Date(0);
		Time time = new Time(0);
		Order order = new Order();
		order.setCustomer(customer);
		order.setDate(date);
		order.setTime(time);
		order.setOrderStatus(orderStatus);
		order.setOrderType(orderType);
		order.addOrderItem(newItem);

		// Save the created Order instance
		Order newOrder = orderRepository.save(order);

		// Creation of a Review instance with test attributes
		String description = "The order was processed quickly";
		Rating rating = Rating.Good;
		Review review = new Review();
		review.setRating(rating);
		review.setDescription(description);

		// Set the order and customer previously created because of the one-to-one and
		// many-to-one associations
		review.setOrder(newOrder);
		review.setCustomer(customer);

		// Save the created Review instance
		Review newReview = reviewRepository.save(review);

		// Set the variable to null, and then try retrieving the saved instance using
		// its id
		review = null;
		review = reviewRepository.findReviewById(newReview.getId());

		// Determine whether the instance is null, if the attribute description matches
		// and if the reference to order matches.
		assertNotNull(review);
		assertEquals(description, review.getDescription());
		assertEquals(order.getId(), review.getOrder().getId()); // Checking by id because it's the order's id

	}

}
