package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.ShoppableItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;

@Service
public class OrderItemService {

	@Autowired
	OrderItemRepository orderItemRepo;
	@Autowired
	ShoppableItemRepository itemRepo;
	@Autowired
	OrderRepository orderRepo;

	/**
	 * Creates an order item in the system
	 * 
	 * @param orderItemId
	 * @param quantity
	 * @param itemName
	 * @param orderId
	 * @return orderItem
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */
	@Transactional
	public OrderItem createOrderItem(int quantity, String itemName, Long orderId)
			throws IllegalArgumentException {

		Order order = orderRepo.findOrderById(orderId);
		if (order == null)
			throw new IllegalArgumentException("Order Item cannot exist without an order. ");

		if (quantity <= 0)
			throw new IllegalArgumentException("Please enter a valid quantity. ");

		ShoppableItem item = itemRepo.findByName(itemName);

		List<OrderItem> list = order.getOrderItems();

		if (!list.isEmpty()) {

			for (int i = 0; i < list.size(); i++) {

				if (list.get(i).getItem().getName().equals(itemName))
					throw new IllegalArgumentException("Item already in cart. ");
			}
		}

		if (item == null)
			throw new IllegalArgumentException("Please enter a valid item. ");

		if (item.getQuantityAvailable() < quantity)
			throw new IllegalArgumentException("Quantity exceeds the amount in inventory. ");

		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(quantity);
		orderItem.setItem(item);
		// orderItem.setId(orderItemId);

		OrderItem newOrderItem = orderItemRepo.save(orderItem);

		return newOrderItem;

	}

	/**
	 * Updates an existing order item
	 * 
	 * @param orderItemId
	 * @param quantity
	 * @param itemName
	 * @param orderId
	 * @return
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */
	@Transactional
	public OrderItem updateOrderItem(Long orderItemId, int quantity, String itemName, Long orderId)
			throws IllegalArgumentException {

		Order order = orderRepo.findOrderById(orderId);
		if (order == null)
			throw new IllegalArgumentException("Order Item cannot exist without an order. ");

		if (quantity <= 0)
			throw new IllegalArgumentException("Please enter a valid quantity. ");

		ShoppableItem item = itemRepo.findByName(itemName);

		if (item == null)
			throw new IllegalArgumentException("Please enter a valid item. ");

		if (item.getQuantityAvailable() < quantity)
			throw new IllegalArgumentException("Quantity exceeds the amount in inventory. ");

		OrderItem orderItem = orderItemRepo.findOrderItemById(orderItemId);
		if (orderItem == null)
			throw new IllegalArgumentException("Please enter a valid order item. ");

		orderItem.setQuantity(quantity);
		orderItem.setItem(item);

		orderItemRepo.save(orderItem);
		return orderItem;
	}

	/**
	 * Deletes an order item from the system
	 * 
	 * @param orderItemId
	 * @return
	 * @throws IllegalArgumentException
	 * @author Karl Rouhana
	 */
	@Transactional
	public boolean deleteOrderItem(Long orderItemId) throws IllegalArgumentException {

		OrderItem orderItem = orderItemRepo.findOrderItemById(orderItemId);
		if (orderItem == null)
			throw new IllegalArgumentException("Please enter a valid order item. ");

		orderItemRepo.delete(orderItem);
		orderItem.delete();
		return true;

	}

	/**
	 * Returns all order items in the system
	 * 
	 * @return
	 * @author Karl Rouhana
	 */
	@Transactional
	public List<OrderItem> getAllOrderItem() {

		List<OrderItem> allOrderItems = toList(orderItemRepo.findAll());

		return allOrderItems;

	}

	/**
	 * Returns a specific order item in the system
	 * 
	 * @param orderItemId
	 * @return
	 * @author Karl Rouhana
	 */
	@Transactional
	public OrderItem getOrderItemById(Long orderItemId) {

		if (orderItemId == null)
			throw new IllegalArgumentException("Please enter a valid order ID. ");
		OrderItem orderItem = orderItemRepo.findOrderItemById(orderItemId);
		if (orderItem == null)
			throw new IllegalArgumentException("Please enter a valid order item by providing a valid order item ID. ");

		return orderItem;

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
