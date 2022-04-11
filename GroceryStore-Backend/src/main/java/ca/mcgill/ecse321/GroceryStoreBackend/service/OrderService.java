package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.StoreRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Store;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	OrderItemRepository orderItemRepo;

	@Autowired
	StoreRepository storeRepo;

	/**
	 *
	 * Creates an order in the system
	 * 
	 * @param aOrderType
	 * @param aOrderStatus
	 * @param aDate
	 * @param aTime
	 * @param email
	 * @return order
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */
	@Transactional
	public Order createOrder(String aOrderType, OrderStatus aOrderStatus, Date aDate, Time aTime, String email)
			throws IllegalArgumentException {

		if (aOrderType == null || aOrderType.equals(""))
			throw new IllegalArgumentException("Please enter a valid order type. ");
		if (aOrderStatus == null)
			throw new IllegalArgumentException("Please enter a valid order status. ");
		if (aDate == null)
			throw new IllegalArgumentException("Please enter a valid date. ");
		if (aTime == null)
			throw new IllegalArgumentException("Please enter a valid time. ");

		Customer aCustomer = customerRepo.findByEmail(email);
		if (aCustomer == null)
			throw new IllegalArgumentException("Please enter a valid customer. ");

		Order order = new Order(convertOrderType(aOrderType), aOrderStatus, aDate, aTime, aCustomer);
		Order newOrder = orderRepo.save(order);
		return newOrder;

	}

	/**
	 * Updates an order that is already in the system
	 * 
	 * @param aOrderStatus
	 * @param orderId
	 * @return order
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */
	@Transactional
	public Order updateOrder(String aOrderStatus, Long orderId)
			throws IllegalArgumentException {

		Order order = orderRepo.findOrderById(orderId);
		if (order == null)
			throw new IllegalArgumentException("Please enter a valid order. ");

		if (aOrderStatus == null)
			throw new IllegalArgumentException("Please enter a valid order status. ");

		if (aOrderStatus.equals("Cancelled")) {
			cancelOrder(orderId);
			return null;
		}

		order.setOrderStatus(convertOrderStatus(aOrderStatus));
		orderRepo.save(order);

		return order;
	}

	/**
	 * Adds an item to the existing order
	 * 
	 * @param orderId
	 * @param orderItemId
	 * @return boolean
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */

	@Transactional
	public boolean addItemsToOrder(Long orderId, Long orderItemId) throws IllegalArgumentException {

		boolean added = false;
		OrderItem item = orderItemRepo.findOrderItemById(orderItemId);
		if (item == null)
			throw new IllegalArgumentException("Please enter a valid item to add to the order. ");

		Order order = orderRepo.findOrderById(orderId);
		if (order == null)
			throw new IllegalArgumentException("Please enter a valid order. ");

		added = order.addOrderItem(item);

		item.getItem().setQuantityAvailable(
				item.getItem().getQuantityAvailable() - item.getQuantity());

		return added;

	}

	/**
	 * Removes an item from the existing order
	 * 
	 * @param orderId
	 * @param orderItemId
	 * @return boolean
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */
	@Transactional
	public boolean deleteItemsToOrder(Long orderId, Long orderItemId) throws IllegalArgumentException {

		boolean removed = false;

		OrderItem item = orderItemRepo.findOrderItemById(orderItemId);
		if (item == null)
			throw new IllegalArgumentException("Please enter a valid item to remove from the order. ");

		Order order = orderRepo.findOrderById(orderId);
		if (order == null)
			throw new IllegalArgumentException("Please enter a valid order. ");

		removed = order.removeOrderItem(item);

		item.getItem().setQuantityAvailable(
				item.getItem().getQuantityAvailable() + item.getQuantity());

		return removed;

	}

	/**
	 * Delete an order from the system
	 * 
	 * @param orderId
	 * @return boolean
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */

	@Transactional
	public boolean cancelOrder(Long orderId) throws IllegalArgumentException {

		Order order = orderRepo.findOrderById(orderId);
		if (order == null) {
			throw new IllegalArgumentException("Please enter a valid order. ");

		}

		List<OrderItem> listOfItems = order.getOrderItems();
		if (listOfItems != null) {

			for (OrderItem item : listOfItems) {

				item.getItem().setQuantityAvailable(
						item.getItem().getQuantityAvailable() + item.getQuantity());

				orderItemRepo.delete(item);
			}

			order.getOrderItems().clear();
		}

		order.setOrderStatus(convertOrderStatus("Cancelled"));
		return true;

	}

	/**
	 * Gets all the orders in the system
	 * 
	 * @return allOrders
	 * @author Karl Rouhana
	 */

	@Transactional
	public List<Order> getAllOrder() {

		List<Order> allOrders = toList(orderRepo.findAll());

		return allOrders;
	}

	/**
	 * Gets all order by a single customer in the system
	 * 
	 * @param email
	 * @return allOrdersByCustomer
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */

	@Transactional
	public List<Order> getAllOrdersByCustomer(String email) throws IllegalArgumentException {

		Customer aCustomer = customerRepo.findByEmail(email);
		if (aCustomer == null)
			throw new IllegalArgumentException("Please enter a valid customer. ");

		List<Order> allOrdersByCustomer;

		allOrdersByCustomer = orderRepo.findOrderByCustomer(aCustomer);

		return allOrdersByCustomer;
	}

	/**
	 * Gets the specified order
	 * 
	 * @param orderId
	 * @return order
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */
	@Transactional
	public Order getOrderById(Long orderId) throws IllegalArgumentException {

		if (orderId == null)
			throw new IllegalArgumentException("Please enter a valid order ID. ");
		Order order = orderRepo.findOrderById(orderId);

		if (order == null)
			throw new IllegalArgumentException("Please enter a valid order by providing a valid order ID. ");

		return order;

	}

	/**
	 * Gets the order price
	 * 
	 * @param orderId
	 * @return total
	 * @throws IllegalArgumentException
	 * @author anaelle.drai
	 */
	@Transactional
	public double getOrderPrice(Long orderId) throws IllegalArgumentException {

		if (orderId == null)
			throw new IllegalArgumentException("Please enter a valid order ID. ");
		Order order = orderRepo.findOrderById(orderId);
		double total = 0;
		for (OrderItem orderItem : order.getOrderItems()) {
			total += orderItem.getQuantity() * orderItem.getItem().getPrice();
		}
		if (order.getOrderType() == OrderType.Delivery) {
			Store store = storeRepo.findStoreById((long) 1);
			Customer tempCustomer = order.getCustomer();

			String customerAddress = tempCustomer.getAddress();
			customerAddress = customerAddress.toLowerCase();

			if (!customerAddress.contains(store.getTown().toLowerCase())) {
				total += store.getDeliveryFee();
			}

		}
		return total;

	}

	/**
	 * Converts from a string to an order type
	 * 
	 * @param type
	 * @return OrderType
	 * @author Karl Rouhana
	 */
	private OrderType convertOrderType(String type) {

		if (type.equals("Delivery"))
			return OrderType.Delivery;
		if (type.equals("PickUp") || type.equals("Pick Up") || type.equals("Pick up") || type.equals("Pickup"))
			return OrderType.PickUp;

		throw new IllegalArgumentException("Please enter a valid order type. ");

	}

	/**
	 * Converts from a string to an order status
	 * 
	 * @param status
	 * @return OrderStatus
	 * @author Karl Rouhana
	 */
	private OrderStatus convertOrderStatus(String status) {

		if (status.equals("Confirmed"))
			return OrderStatus.Confirmed;
		if (status.equals("Preparing"))
			return OrderStatus.Preparing;
		if (status.equals("Cancelled"))
			return OrderStatus.Cancelled;
		if (status.equals("Delivering"))
			return OrderStatus.Delivering;
		if (status.equals("Ready"))
			return OrderStatus.Ready;
		if (status.equals("Fulfilled"))
			return OrderStatus.Fulfilled;

		throw new IllegalArgumentException("Please enter a valid order status. ");

	}

	/**
	 * Helper method to return a list of iterable object
	 * 
	 * @param <T>
	 * @param iterable
	 * @return resultList
	 */
	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
