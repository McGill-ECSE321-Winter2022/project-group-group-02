package ca.mcgill.ecse321.GroceryStoreBackend.service;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.ShoppableItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;

@ExtendWith(MockitoExtension.class)
public class TestOrderService {

	@Mock
	private OrderRepository orderRepo;
	@Mock
	private CustomerRepository customerRepo;

	@Mock
	private OrderItemRepository orderItemRepo;
	@Mock
	private ShoppableItemRepository shoppableItemRepo;

	@InjectMocks
	private OrderService orderService;
	@InjectMocks
	private CustomerService customerService;
	@InjectMocks
	private OrderItemService orderItemService;

	private static final String CUSTOMER_EMAIL = "theBestValuableCustomer@lollar.com";
	private static final String CUSTOMER_NAME = "Bank";
	private static final String CUSTOMER_ADDRESS = "Beirut, the land of opportunities";
	private static final String CUSTOMER_PASSWORD = "Audi2019";

	private static final Long ORDER_ID = 1234567L;
	private static final OrderType ORDER_TYPE = OrderType.PickUp;
	private static final OrderStatus ORDER_STATUS = OrderStatus.Confirmed;
	private static final Date ORDER_DATE = Date.valueOf("2022-03-10");
	private static final Time ORDER_TIME = Time.valueOf("10:09:00");

	private static final String SHOPPABLE_ITEM_NAME = "FRESH LOLLAR";
	private static final double SHOPPABLE_ITEM_PRICE = 22900;
	private static final int SHOPPABLE_ITEM_QUANTITY = 20;

	private static final int ORDER_ITEM_QUANTITY = 1;
	private static final long ORDER_ITEM_ID = 123456L;

	@BeforeEach
	public void setMockOutput() {

		lenient().when(orderRepo.findOrderById(any(Long.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ORDER_ID)) {

				Customer customer = new Customer();
				customer.setEmail(CUSTOMER_EMAIL);
				customer.setName(CUSTOMER_NAME);
				customer.setAddress(CUSTOMER_ADDRESS);
				customer.setPassword(CUSTOMER_PASSWORD);

				Order order = new Order();
				order.setCustomer(customer);
				order.setId(ORDER_ID);
				order.setOrderStatus(ORDER_STATUS);
				order.setOrderType(ORDER_TYPE);
				order.setDate(ORDER_DATE);
				order.setTime(ORDER_TIME);

				return order;
			} else {
				return null;
			}

		});

		lenient().when(orderRepo.findAll()).thenAnswer((InvocationOnMock invocation) -> {

			List<Order> list = new ArrayList<>();

			Customer customer = new Customer();
			customer.setEmail(CUSTOMER_EMAIL);
			customer.setName(CUSTOMER_NAME);
			customer.setAddress(CUSTOMER_ADDRESS);
			customer.setPassword(CUSTOMER_PASSWORD);

			Order order = new Order();
			order.setCustomer(customer);
			order.setId(ORDER_ID);
			order.setOrderStatus(ORDER_STATUS);
			order.setOrderType(ORDER_TYPE);
			order.setDate(ORDER_DATE);
			order.setTime(ORDER_TIME);

			list.add(order);
			return list;

		});

		lenient().when(orderRepo.findOrderByCustomer(any(Customer.class))).thenAnswer((InvocationOnMock invocation) -> {

			List<Order> list = new ArrayList<>();

			Customer customer = new Customer();
			customer.setEmail(CUSTOMER_EMAIL);
			customer.setName(CUSTOMER_NAME);
			customer.setAddress(CUSTOMER_ADDRESS);
			customer.setPassword(CUSTOMER_PASSWORD);

			Order order = new Order();
			order.setCustomer(customer);
			order.setId(ORDER_ID);
			order.setOrderStatus(ORDER_STATUS);
			order.setOrderType(ORDER_TYPE);
			order.setDate(ORDER_DATE);
			order.setTime(ORDER_TIME);

			list.add(order);
			return list;

		});

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

		lenient().when(shoppableItemRepo.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(SHOPPABLE_ITEM_NAME)) {
				ShoppableItem shoppableItem = new ShoppableItem();
				shoppableItem.setName(SHOPPABLE_ITEM_NAME);
				shoppableItem.setPrice(SHOPPABLE_ITEM_PRICE);
				shoppableItem.setQuantityAvailable(SHOPPABLE_ITEM_QUANTITY);

				return shoppableItem;
			} else {
				return null;
			}
		});

		lenient().when(orderItemRepo.findOrderItemById(any(Long.class))).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(ORDER_ITEM_ID)) {

				ShoppableItem item = new ShoppableItem();
				item.setName(SHOPPABLE_ITEM_NAME);
				item.setQuantityAvailable(SHOPPABLE_ITEM_QUANTITY);
				item.setPrice(SHOPPABLE_ITEM_PRICE);

				OrderItem orderItem = new OrderItem();

				orderItem.setItem(item);
				orderItem.setId(ORDER_ITEM_ID);
				orderItem.setQuantity(ORDER_ITEM_QUANTITY);

				return orderItem;
			} else {
				return null;
			}

		});

		Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
			return invocation.getArgument(0);
		};
		lenient().when(orderItemRepo.save(any(OrderItem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(shoppableItemRepo.save(any(ShoppableItem.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(customerRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
		lenient().when(orderRepo.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);

	}

	/**
	 * tests for create order
	 */

	@Test
	public void testCreateOrder() {
		String name = "nameTest";
		String password = "passwordTest1";
		String email = "customer@localTown.com";
		String address = "1325 Depaneur Kiwi";

		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		Long orderId = 558L;
		String aOrderType = "PickUp";
		OrderStatus aOrderStatus = OrderStatus.Confirmed;
		Date date = Date.valueOf("2022-03-10");
		Time time = Time.valueOf("08:00:00");

		Order order = null;

		try {
			order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);

		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(order);
		assertEquals(aOrderType, order.getOrderType());
		assertEquals(aOrderStatus, order.getOrderStatus());
		assertEquals(time, order.getTime());
		assertEquals(date, order.getDate());
		assertEquals(customer, order.getCustomer());
		assertEquals(orderId, order.getId());

	}

	@Test
	public void testCreateOrderNullCustomer() {

		String email = "noOneHas@thatEmail.com";

		Long orderId = 557L;
		String aOrderType ="PickUp";
		OrderStatus aOrderStatus = OrderStatus.Confirmed;
		Date date = Date.valueOf("2022-01-10");
		Time time = Time.valueOf("06:00:00");

		Order order = null;
		String error = null;

		try {
			order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);

		} catch (IllegalArgumentException e) {

			error = e.getMessage();
		}
		assertNull(order);
		assertEquals("Please enter a valid customer. ", error);

	}

	@Test
	public void testCreateOrderNullOrderType() {

		String name = "Hassan Diab";
		String password = "passwordTest1";
		String email = "Hassoun@localTown.com";
		String address = "1325 Depaneur Jamhouri";

		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		Long orderId = 537L;
		String aOrderType = null;
		OrderStatus aOrderStatus = OrderStatus.Confirmed;
		Date date = Date.valueOf("2022-03-15");
		Time time = Time.valueOf("08:45:38");

		Order order = null;
		String error = null;

		try {
			order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);

		} catch (IllegalArgumentException e) {

			error = e.getMessage();
		}
		assertNull(order);
		assertEquals("Please enter a valid order type. ", error);

	}

	@Test
	public void testCreateOrderNullOrderStatus() {

		String name = "Samir Abdel Hak";
		String password = "passwordTest1";
		String email = "Samir@localTown.com";
		String address = "8564 Jamhouri Daou Kiwi";

		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		Long orderId = 5874L;
		String aOrderType = "PickUp";
		OrderStatus aOrderStatus = null;
		Date date = Date.valueOf("2022-04-10");
		Time time = Time.valueOf("08:04:00");

		Order order = null;
		String error = null;

		try {
			order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);

		} catch (IllegalArgumentException e) {

			error = e.getMessage();
		}
		assertNull(order);
		assertEquals("Please enter a valid order status. ", error);

	}

	@Test
	public void testCreateOrderNullTime() {

		String name = "Louay";
		String password = "passwordTest1";
		String email = "Louay@localTown.com";
		String address = "Rue Mar Takla Depaneur Kiwi";

		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		Long orderId = 55894L;
		String aOrderType = "PickUp";
		OrderStatus aOrderStatus = OrderStatus.Confirmed;
		Date date = Date.valueOf("2022-03-10");
		Time time = null;

		Order order = null;
		String error = null;

		try {
			order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);

		} catch (IllegalArgumentException e) {

			error = e.getMessage();
		}
		assertNull(order);
		assertEquals("Please enter a valid time. ", error);

	}

	@Test
	public void testCreateOrderNullDate() {

		String name = "Mark";
		String password = "passwordTest1";
		String email = "mark@localTown.com";
		String address = "Rue Salim Sleim";

		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		Long orderId = 5536L;
		String aOrderType = "PickUp";
		OrderStatus aOrderStatus = OrderStatus.Confirmed;
		Date date = null;
		Time time = Time.valueOf("14:45:00");

		Order order = null;
		String error = null;

		try {
			order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);

		} catch (IllegalArgumentException e) {

			error = e.getMessage();
		}
		assertNull(order);
		assertEquals("Please enter a valid date. ", error);

	}

	@Test
	public void testCreateOrderWithOrderIdAlreadyTaken() {
		String name = "Karl";
		String password = "passwordTest1";
		String email = "karl@localTown.com";
		String address = "1325 Depaneur Pa Kiwi";

		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		String aOrderType ="PickUp";
		OrderStatus aOrderStatus = OrderStatus.Confirmed;
		Date date = Date.valueOf("2022-05-10");
		Time time = Time.valueOf("08:54:00");

		Order order = null;
		String error = null;
		try {
			order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, ORDER_ID);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(order);
		assertEquals(error, "Order with ID already exists.");

	}

	// -----------------------------------------------------------------------------------------------------------------------------------//

	/**
	 * tests for update order
	 */

	@Test
	public void testUpdateOrder() {

		String aOrderStatus = "Confirmed";


		Order order = null;

		try {
			order = orderService.updateOrder( aOrderStatus, ORDER_ID);

		} catch (IllegalArgumentException e) {
			fail();
		}
		assertNotNull(order);

		assertEquals(aOrderStatus, order.getOrderStatus());
		assertEquals(CUSTOMER_EMAIL, order.getCustomer().getEmail());
		assertEquals(ORDER_ID, order.getId());

	}

	

	@Test
	public void testUpdateOrderNullStatus() {

		String aOrderStatus = null;


		Order order = null;
		String error = null;
		try {
			order = orderService.updateOrder(aOrderStatus,  ORDER_ID);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		assertNull(order);

		assertEquals(error, "Please enter a valid order status. ");

	}

	
	@Test
	public void testUpdateOrderWithInvalidOrderId() {

		String aOrderStatus = "Confirmed";


		Long fakeId = 34L;
		Order order = null;
		String error = null;
		try {
			order = orderService.updateOrder( aOrderStatus,  fakeId);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();

		}
		assertNull(order);
		assertEquals(error, "Please enter a valid order. ");

	}

//-----------------------------------------------------------------------------------------------------------------------------------//

	/**
	 * Tests for add items to order
	 */

	@Test
	public void testAddItemsToOrder() {

		boolean added = false;
		Order order = null;
		OrderItem orderItem = null;
		try {

			order = orderService.getOrderById(ORDER_ID);
			lenient().when(orderRepo.findOrderById(ORDER_ID)).thenReturn(order);
			orderItem = orderItemService.getOrderItemById(ORDER_ITEM_ID);
			lenient().when(orderItemRepo.findOrderItemById(ORDER_ITEM_ID)).thenReturn(orderItem);

			added = orderService.addItemsToOrder(ORDER_ID, ORDER_ITEM_ID);

		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(added);
		assertEquals(orderItem, order.getOrderItems().get(0));
		assertEquals(1, order.getOrderItems().size());

	}

	@Test
	public void testAddItemsToOrderInvalidOrderItem() {

		boolean added = false;
		Order order = null;
		OrderItem orderItem = null;
		String error = null;
		Long fakeOrderItemId = 1256L;

		try {
			added = orderService.addItemsToOrder(ORDER_ID, fakeOrderItemId);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertFalse(added);
		assertEquals(error, "Please enter a valid item to add to the order. ");
	}

	@Test
	public void testAddItemsToOrderInvalidOrder() {

		boolean added = false;
		Order order = null;
		OrderItem orderItem = null;
		String error = null;
		Long fakeOrderId = 1256L;

		try {
			added = orderService.addItemsToOrder(fakeOrderId, ORDER_ITEM_ID);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertFalse(added);
		assertEquals(error, "Please enter a valid order. ");
	}

//-----------------------------------------------------------------------------------------------------------------------------------//

	/**
	 * Tests for remove items from order
	 */

	@Test
	public void testRemoveItemsToOrder() {

		boolean removed = false;
		Order order = null;
		OrderItem orderItem = null;
		try {
			order = orderService.getOrderById(ORDER_ID);
			lenient().when(orderRepo.findOrderById(ORDER_ID)).thenReturn(order);
			orderItem = orderItemService.getOrderItemById(ORDER_ITEM_ID);
			lenient().when(orderItemRepo.findOrderItemById(ORDER_ITEM_ID)).thenReturn(orderItem);
			orderService.addItemsToOrder(ORDER_ID, ORDER_ITEM_ID);

			removed = orderService.deleteItemsToOrder(ORDER_ID, ORDER_ITEM_ID);

		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(removed);
		assertEquals(0, order.getOrderItems().size());

	}

	@Test
	public void testRemoveItemsToOrderInvalidOrderItem() {

		boolean removed = false;
		Order order = null;
		OrderItem orderItem = null;
		String error = null;
		Long fakeOrderItemId = 1256L;

		try {
			removed = orderService.deleteItemsToOrder(ORDER_ID, fakeOrderItemId);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertFalse(removed);
		assertEquals(error, "Please enter a valid item to remove from the order. ");
	}

	@Test
	public void testRemoveItemsToOrderInvalidOrder() {

		boolean removed = false;
		Order order = null;
		OrderItem orderItem = null;
		String error = null;
		Long fakeOrderId = 1256L;

		try {
			removed = orderService.addItemsToOrder(fakeOrderId, ORDER_ITEM_ID);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertFalse(removed);
		assertEquals(error, "Please enter a valid order. ");
	}

	// -----------------------------------------------------------------------------------------------------------------------------------//

	/**
	 * Tests for cancel order
	 */

	@Test
	public void testCancelOrder() {

		String name = "nameTest";
		String password = "passwordTest1";
		String email = "customer@localTown.com";
		String address = "1325 Depaneur Kiwi";

		Customer customer = customerService.createCustomer(email, password, name, address);
		lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

		Long orderId = 558L;
		String aOrderType ="PickUp";
		OrderStatus aOrderStatus = OrderStatus.Confirmed;
		Date date = Date.valueOf("2022-03-10");
		Time time = Time.valueOf("08:00:00");

		Order order = null;
		order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);
		lenient().when(orderRepo.findOrderById(orderId)).thenReturn(order);

		boolean deleted = false;

		try {
			deleted = orderService.cancelOrder(orderId);

		} catch (IllegalArgumentException e) {
			fail();
		}

		assertTrue(deleted);

	}

	@Test
	public void testCancelOrderWithInvalidOrder() {

		Long orderId = 558L;

		String error = null;

		try {
			orderService.cancelOrder(orderId);

		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		assertEquals(error, "Please enter a valid order. ");

	}

	// -----------------------------------------------------------------------------------------------------------------------------------//

	/**
	 * Tests for get order by Id
	 */

	@Test
	public void testGetOrderById() {

		Order order = null;

		try {
			order = orderService.getOrderById(ORDER_ID);

		} catch (IllegalArgumentException e) {

			fail();
		}

		assertEquals(order.getId(), ORDER_ID);
		assertNotNull(order);

	}

	@Test
	public void testGetOrderByIdButInvalid() {

		Order order = null;
		Long fakeId = 98L;
		String error = null;
		try {
			order = orderService.getOrderById(fakeId);

		} catch (IllegalArgumentException e) {

			error = e.getMessage();
		}

		assertEquals(error, "Please enter a valid order by providing a valid order ID. ");
		assertNull(order);

	}

	
	// -----------------------------------------------------------------------------------------------------------------------------------//

	/**
	 * test to get all orders
	 */
	@Test
	public void testGetAllOrders() {

		List<Order> allOrders = null;
		try {

			allOrders = orderService.getAllOrder();
			lenient().when(orderRepo.findAll()).thenReturn(allOrders);

		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(allOrders);
		assertEquals(1, allOrders.size());

	}

	/**
	 * test to get all orders by customer
	 */
	@Test
	public void testGetAllOrdersByCustomer() {

		List<Order> allOrders = null;
		try {
			Customer customer = customerService.getCustomer(CUSTOMER_EMAIL);
			allOrders = orderService.getAllOrdersByCustomer(CUSTOMER_EMAIL);
			lenient().when(orderRepo.findOrderByCustomer(customer)).thenReturn(allOrders);

		} catch (IllegalArgumentException e) {
			fail();
		}

		assertNotNull(allOrders);
		assertEquals(1, allOrders.size());

	}

}
