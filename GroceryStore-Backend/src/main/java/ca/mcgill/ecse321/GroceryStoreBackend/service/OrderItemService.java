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

	@Transactional
	public OrderItem createOrderItem(Long orderItemId, int quantity, String itemName, Long orderId)
			throws IllegalArgumentException {

		Order order = orderRepo.findOrderById(orderId);
		if (order == null)
			throw new IllegalArgumentException("Order Item cannot exist without an order. ");

		if (quantity < 0)
			throw new IllegalArgumentException("Please enter a valid quantity. ");

		ShoppableItem item = itemRepo.findByName(itemName);

		if (item == null)
			throw new IllegalArgumentException("Please enter a valid item. ");

		if (orderItemRepo.existsById(orderItemId) == true)
			throw new IllegalArgumentException("This ID is being used. ");

		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(quantity);
		orderItem.setItem(item);
		orderItem.setId(orderItemId);

		orderItemRepo.save(orderItem);

		return orderItem;

	}

	@Transactional
	public OrderItem updateOrderItem(Long orderItemId, int quantity, String itemName, Long orderId)
			throws IllegalArgumentException {

		Order order = orderRepo.findOrderById(orderId);
		if (order == null)
			throw new IllegalArgumentException("Order Item cannot exist without an order. ");

		if (quantity < 0)
			throw new IllegalArgumentException("Please enter a valid quantity. ");

		ShoppableItem item = itemRepo.findByName(itemName);

		if (item == null)
			throw new IllegalArgumentException("Please enter a valid item. ");

		OrderItem orderItem = orderItemRepo.findOrderItemById(orderItemId);
		if (orderItem == null)
			throw new IllegalArgumentException("Please enter a valid order item. ");

		orderItem.setQuantity(quantity);
		orderItem.setItem(item);

		orderItemRepo.save(orderItem);
		return orderItem;
	}

	@Transactional
	public boolean deleteOrderItem(Long orderItemId) throws IllegalArgumentException {

		OrderItem orderItem = orderItemRepo.findOrderItemById(orderItemId);
		if (orderItem == null)
			throw new IllegalArgumentException("Please enter a valid order item. ");

		orderItemRepo.delete(orderItem);
		orderItem.delete();
		return true;

	}

	@Transactional
	public List<OrderItem> getAllOrderItem() {

		List<OrderItem> allOrderItems = toList(orderItemRepo.findAll());

		if (allOrderItems.size() == 0)
			throw new IllegalArgumentException("There's no order items in the system. ");

		return allOrderItems;

	}

	@Transactional
	public OrderItem getOrderItemById(Long orderItemId) {

		if (orderItemId == null)
			throw new IllegalArgumentException("Please enter a valid order ID. ");
		OrderItem orderItem = orderItemRepo.findOrderItemById(orderItemId);
		if (orderItem == null)
			throw new IllegalArgumentException("Please enter a valid order item by providing a valid order item ID. ");

		return orderItem;

	}

	private <T> List<T> toList(Iterable<T> iterable) {
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}

}
