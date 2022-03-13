package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import java.sql.Date;
import java.sql.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Review.Rating;

@ExtendWith(MockitoExtension.class)
public class TestReviewService {

	@Mock
	private ReviewRepository reviewRepo;

	@Mock
	private CustomerRepository customerRepo;

	@Mock
	private OrderRepository orderRepo;

	@InjectMocks
	private ReviewService reviewService;

	@InjectMocks
	private CustomerService customerService;

	@InjectMocks
	private OrderService orderService;

	private static final String CUSTOMER_EMAIL = "TestCustomer@mail.com";
	private static final String CUSTOMER_NAME = "Test";
	private static final String CUSTOMER_ADDRESS = "35 St Catherine O, Montreal";
	private static final String CUSTOMER_PASSWORD = "2222";

	private static final String REVIEW_DESCRIPTION = "It was alright";
	private static final Rating REVIEW_RATING = Rating.Good;
	private static final Long REVIEW_ID = 4236L;

	private static final Long ORDER_ID = 4236L;
	private static final OrderType ORDER_TYPE = OrderType.Delivery;
	private static final OrderStatus ORDER_STATUS = OrderStatus.Preparing;
	private static final Date ORDER_DATE = Date.valueOf("2022-02-20");
	private static final Time ORDER_TIME = Time.valueOf("10:15:00");;

	@BeforeEach
	public void setMockOutput() {

		lenient().when(customerRepo.findByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(CUSTOMER_EMAIL)) {
				Customer customer = new Customer();
				customer.setEmail(CUSTOMER_EMAIL);
				customer.setName(CUSTOMER_NAME);
				customer.setAddress(CUSTOMER_ADDRESS);
				customer.setPassword(CUSTOMER_PASSWORD);
				return customer;
			} else {
				return null;
			}

		});

		lenient().when(orderRepo.findOrderById(any(Long.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ORDER_ID)) {

				Customer customer = new Customer();
				customer.setEmail(CUSTOMER_EMAIL);
				customer.setName(CUSTOMER_NAME);
				customer.setAddress(CUSTOMER_ADDRESS);
				customer.setPassword(CUSTOMER_PASSWORD);

				Order order = new Order();
				order.setId(ORDER_ID);
				order.setOrderStatus(ORDER_STATUS);
				order.setOrderType(ORDER_TYPE);
				order.setDate(ORDER_DATE);
				order.setTime(ORDER_TIME);
				order.setCustomer(customer);

				return order;
			} else {
				return null;
			}

		});

		lenient().when(reviewRepo.findReviewById(any(Long.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(REVIEW_ID)) {

				Customer customer = new Customer();
				customer.setEmail(CUSTOMER_EMAIL);
				customer.setName(CUSTOMER_NAME);
				customer.setAddress(CUSTOMER_ADDRESS);
				customer.setPassword(CUSTOMER_PASSWORD);

				Order order = new Order();
				order.setId(ORDER_ID);
				order.setOrderStatus(ORDER_STATUS);
				order.setOrderType(ORDER_TYPE);
				order.setDate(ORDER_DATE);
				order.setTime(ORDER_TIME);
				order.setCustomer(customer);

				Review review = new Review();

				review.setDescription(REVIEW_DESCRIPTION);
				review.setRating(REVIEW_RATING);
				review.setId(REVIEW_ID);
				review.setOrder(order);
				review.setCustomer(customer);

				return review;
			} else {

				return null;
			}

		});

		lenient().when(reviewRepo.findReviewByOrder(any(Order.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (((Order) invocation.getArgument(0)).getId().equals(ORDER_ID)) {

				Customer customer = new Customer();
				customer.setEmail(CUSTOMER_EMAIL);
				customer.setName(CUSTOMER_NAME);
				customer.setAddress(CUSTOMER_ADDRESS);
				customer.setPassword(CUSTOMER_PASSWORD);

				Order order = new Order();
				order.setId(ORDER_ID);
				order.setOrderStatus(ORDER_STATUS);
				order.setOrderType(ORDER_TYPE);
				order.setDate(ORDER_DATE);
				order.setTime(ORDER_TIME);
				order.setCustomer(customer);

				Review review = new Review();

				review.setDescription(REVIEW_DESCRIPTION);
				review.setRating(REVIEW_RATING);
				review.setId(REVIEW_ID);
				review.setOrder(order);
				review.setCustomer(customer);

				return review;
			} else {

				return null;
			}

		});

		lenient().when(reviewRepo.findByCustomerAndOrder(any(Customer.class), any(Order.class)))
				.thenAnswer((InvocationOnMock invocation) -> {
					if (((Order) invocation.getArgument(1)).getId().equals(ORDER_ID)
							&& ((Customer) invocation.getArgument(0)).getEmail().equals(CUSTOMER_EMAIL)) {

						Customer customer = new Customer();
						customer.setEmail(CUSTOMER_EMAIL);
						customer.setName(CUSTOMER_NAME);
						customer.setAddress(CUSTOMER_ADDRESS);
						customer.setPassword(CUSTOMER_PASSWORD);

						Order order = new Order();
						order.setId(ORDER_ID);
						order.setOrderStatus(ORDER_STATUS);
						order.setOrderType(ORDER_TYPE);
						order.setDate(ORDER_DATE);
						order.setTime(ORDER_TIME);
						order.setCustomer(customer);

						Review review = new Review();

						review.setDescription(REVIEW_DESCRIPTION);
						review.setRating(REVIEW_RATING);
						review.setId(REVIEW_ID);
						review.setOrder(order);
						review.setCustomer(customer);

						return review;
					} else {

						return null;
					}

				});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};

		lenient().when(customerRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(orderRepo.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(reviewRepo.save(any(Review.class))).thenAnswer(returnParameterAsAnswer);
	}

	@Test
	public void testCreateReview() {
		assertEquals(0, reviewService.getAllReviews().size());

		String email = "Customer@mail.com";
		String name = "customerName";
		String address = "Durocher";
		String password = "222";
		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		OrderStatus orderStatus = OrderStatus.Preparing;
		OrderType orderType = OrderType.Delivery;
		Date orderDate = Date.valueOf("2022-02-02");
		Time orderTime = Time.valueOf("11:10:09");
		Long orderId = 21L;
		Order order = new Order();
		order.setOrderStatus(orderStatus);
		order.setOrderType(orderType);
		order.setDate(orderDate);
		order.setTime(orderTime);
		order.setId(orderId);
		order.setCustomer(customer);
		lenient().when(orderRepo.findOrderById(orderId)).thenReturn(order);

		Review review = null;
		String description = "Really Cool";
		Rating rating = Rating.VeryGood;
		Long reviewId = 31L;

		try {
			review = reviewService.createReview(rating, description, email, orderId, reviewId);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(review);
		assertEquals(description, review.getDescription());
		assertEquals(orderId, review.getOrder().getId());
		assertEquals(email, review.getCustomer().getEmail());
		assertEquals(rating, review.getRating());

	}

	@Test
	public void testCreateReviewNullOrder() {
		assertEquals(0, reviewService.getAllReviews().size());

		Long nonExistingOrderId = 21L;

		Review review = null;
		String description = "Really Cool";
		Rating rating = Rating.VeryGood;
		Long reviewId = 31L;
		String error = null;

		try {
			review = reviewService.createReview(rating, description, CUSTOMER_EMAIL, nonExistingOrderId, reviewId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("No order found", error);
	}

	@Test
	public void testCreateReviewNullCustomer() {
		assertEquals(0, reviewService.getAllReviews().size());

		String nonExistingemail = "nobody@mail.com";

		Review review = null;
		String description = "Really Cool";
		Rating rating = Rating.VeryGood;
		Long reviewId = 31L;
		String error = null;

		try {
			review = reviewService.createReview(rating, description, nonExistingemail, ORDER_ID, reviewId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("No customer found", error);
	}

	@Test
	public void testCreateReviewNullDescription() {
		assertEquals(0, reviewService.getAllReviews().size());

		Review review = null;
		String description = null;
		Rating rating = Rating.VeryGood;
		Long reviewId = 31L;
		String error = null;

		try {
			review = reviewService.createReview(rating, description, CUSTOMER_EMAIL, ORDER_ID, reviewId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("Description cannot be null", error);
	}

	@Test
	public void testCreateReviewEmptyDescription() {
		assertEquals(0, reviewService.getAllReviews().size());

		Review review = null;
		String description = "  ";
		Rating rating = Rating.VeryGood;
		Long reviewId = 31L;
		String error = null;

		try {
			review = reviewService.createReview(rating, description, CUSTOMER_EMAIL, ORDER_ID, reviewId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("Description cannot be empty", error);
	}

	@Test
	public void testCreateReviewForOrderNotBelongingToCustomer() {
		assertEquals(0, reviewService.getAllReviews().size());

		String email = "Customer@mail.com";
		String name = "customerName";
		String address = "Durocher";
		String password = "222";
		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		Review review = null;
		String description = "Really Cool";
		Rating rating = Rating.VeryGood;
		Long reviewId = 31L;
		String error = null;

		try {
			review = reviewService.createReview(rating, description, email, ORDER_ID, reviewId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("Order not found for customer", error);
	}

	@Test
	public void testCreateDuplicateReview() {
		assertEquals(0, reviewService.getAllReviews().size());

		Review review = null;
		String description = "Very Cool";
		Rating rating = Rating.VeryGood;
		Long reviewId = 1223213L;
		String error = null;

		try {
			review = reviewService.createReview(rating, description, CUSTOMER_EMAIL, ORDER_ID, reviewId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("Review already exists for this order", error);
	}

	@Test
	public void testCreateReviewWithDuplicateId() {
		assertEquals(0, reviewService.getAllReviews().size());

		String email = "Customer@mail.com";
		String name = "customerName";
		String address = "Durocher";
		String password = "222";
		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		OrderStatus orderStatus = OrderStatus.Preparing;
		OrderType orderType = OrderType.Delivery;
		Date orderDate = Date.valueOf("2022-02-02");
		Time orderTime = Time.valueOf("11:10:09");
		Long orderId = 21L;
		Order order = new Order();
		order.setOrderStatus(orderStatus);
		order.setOrderType(orderType);
		order.setDate(orderDate);
		order.setTime(orderTime);
		order.setId(orderId);
		order.setCustomer(customer);
		lenient().when(orderRepo.findOrderById(orderId)).thenReturn(order);

		Review review = null;
		String description = "Really Cool";
		Rating rating = Rating.VeryGood;
		Long reviewId = REVIEW_ID;
		String error = null;

		try {
			review = reviewService.createReview(rating, description, email, orderId, reviewId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("Review with this id already exists", error);
	}

	@Test
	public void testUpdateReview() {
		assertEquals(0, reviewService.getAllReviews().size());

		Review review = null;
		String description = "Very Cool";
		Rating rating = Rating.VeryGood;

		try {
			review = reviewService.updateReview(ORDER_ID, description, rating);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(review);
		assertEquals(description, review.getDescription());
		assertEquals(ORDER_ID, review.getOrder().getId());
		assertEquals(CUSTOMER_EMAIL, review.getCustomer().getEmail());
		assertEquals(rating, review.getRating());
	}

	@Test
	public void testUpdateReviewNonExistingOrder() {
		assertEquals(0, reviewService.getAllReviews().size());

		Long fakeOrderId = 213L;

		Review review = null;
		String description = "Very Cool";
		Rating rating = Rating.VeryGood;
		String error = null;

		try {
			review = reviewService.updateReview(fakeOrderId, description, rating);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("No order found", error);
	}

	@Test
	public void testUpdateReviewNonExisting() {
		assertEquals(0, reviewService.getAllReviews().size());

		String email = "Customer@mail.com";
		String name = "customerName";
		String address = "Durocher";
		String password = "222";
		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		OrderStatus orderStatus = OrderStatus.Preparing;
		OrderType orderType = OrderType.Delivery;
		Date orderDate = Date.valueOf("2022-02-02");
		Time orderTime = Time.valueOf("11:10:09");
		Long orderIdNoReview = 21L;
		Order order = new Order();
		order.setOrderStatus(orderStatus);
		order.setOrderType(orderType);
		order.setDate(orderDate);
		order.setTime(orderTime);
		order.setId(orderIdNoReview);
		order.setCustomer(customer);
		lenient().when(orderRepo.findOrderById(orderIdNoReview)).thenReturn(order);

		Review review = null;
		String description = "Very Cool";
		Rating rating = Rating.VeryGood;
		String error = null;

		try {
			review = reviewService.updateReview(orderIdNoReview, description, rating);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("Cannot update non existing review", error);
	}

	@Test
	public void testUpdateReviewNullDescription() {
		assertEquals(0, reviewService.getAllReviews().size());

		Review review = null;
		String description = null;
		Rating rating = Rating.VeryGood;
		String error = null;

		try {
			review = reviewService.updateReview(ORDER_ID, description, rating);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("Description cannot be null", error);
	}

	@Test
	public void testUpdateReviewEmptyDescription() {
		assertEquals(0, reviewService.getAllReviews().size());

		Review review = null;
		String description = "  ";
		Rating rating = Rating.VeryGood;
		String error = null;

		try {
			review = reviewService.updateReview(ORDER_ID, description, rating);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertNull(review);
		assertEquals("Description cannot be empty", error);
	}

	@Test
	public void testDeleteReview() {
		assertEquals(0, reviewService.getAllReviews().size());

		boolean didDelete = false;

		try {
			didDelete = reviewService.deleteReview(ORDER_ID);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(didDelete);
	}

	@Test
	public void testDeleteReviewNullOrder() {
		assertEquals(0, reviewService.getAllReviews().size());

		Long fakeOrderId = 2132L;
		String error = null;

		try {
			reviewService.deleteReview(fakeOrderId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals("No order found", error);
	}

	@Test
	public void testGetReviewForOrder() {
		assertEquals(0, reviewService.getAllReviews().size());

		Review review = null;

		try {
			review = reviewService.getReviewForOrder(ORDER_ID);
		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(review);
		assertEquals(REVIEW_DESCRIPTION, review.getDescription());
		assertEquals(ORDER_ID, review.getOrder().getId());
		assertEquals(CUSTOMER_EMAIL, review.getCustomer().getEmail());
		assertEquals(REVIEW_RATING, review.getRating());
	}

	@Test
	public void testGetReviewForNonExistingOrder() {
		assertEquals(0, reviewService.getAllReviews().size());

		Long fakeOrderId = 213243L;
		String error = null;

		try {
			reviewService.getReviewForOrder(fakeOrderId);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "No order found");
	}

}
