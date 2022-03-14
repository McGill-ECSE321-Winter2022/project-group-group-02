package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.OrderItemDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.ShoppableItemDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.service.OrderItemService;
import ca.mcgill.ecse321.GroceryStoreBackend.service.OrderService;

@CrossOrigin(origins = "*")
@RestController
public class OrderItemController {

	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private OrderService orderService;

	/**
	 * View all order item in the system
	 * @return listOfOrderItemDto
	 * @author Karl Rouhana
	 */
	@GetMapping(value = { "/view_all_order_items", "/view_all_order_items/" })
	public List<OrderItemDto> getAllOrders() {
		return orderItemService.getAllOrderItem().stream().map(orderItem -> convertToDTO(orderItem))
				.collect(Collectors.toList());
	}
	
    /**
     * View a specific order item in the system
     * @param orderItemId
     * @return OrderItemDto
     * @author Karl Rouhana
     */
	@GetMapping(value = { "/view_order_item", "/view_order_item/" })
	public OrderItemDto viewOrderForCustomer(@RequestParam("orderItemId") Long orderItemId) {

		return convertToDTO(orderItemService.getOrderItemById(orderItemId));
	}

	/**
	 * Creates an order item in the system
	 * @param quantity
	 * @param itemName
	 * @param orderItemId
	 * @param orderId
	 * @return OrderItemDTO
	 * @author Karl Rouhana
	 */
	@PostMapping(value = { "/create_order_item", "/create_order_item/" })
	public ResponseEntity<?> createOrderItem(@RequestParam("quantity") int quantity,
			@RequestParam("itemName") String itemName, @RequestParam("orderItemId") Long orderItemId,
			@RequestParam("orderId") Long orderId) {

		OrderItem orderItem = null;

		try {
			orderItem = orderItemService.createOrderItem(orderItemId, quantity, itemName, orderId);
			orderService.addItemsToOrder(orderId, orderItemId);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(orderItem), HttpStatus.CREATED);
	}
	

	/**
	 * Updates an order item in the system
	 * @param quantity
	 * @param itemName
	 * @param orderItemId
	 * @param orderId
	 * @return OrderItemDto
	 * @author Karl Rouhana
	 */
	@PutMapping(value = { "/update_order_item", "/update_order_item/" })
	public ResponseEntity<?> updateOrderItem(@RequestParam("quantity") int quantity,
			@RequestParam("itemName") String itemName, @RequestParam("orderItemId") Long orderItemId,
			@RequestParam("orderId") Long orderId) {

		OrderItem orderItem = null;

		try {
			orderItem = orderItemService.updateOrderItem(orderItemId, quantity, itemName, orderId);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(orderItem), HttpStatus.CREATED);
	}
	
	
    /**
     * Deletes an order item in the system
     * @param orderItemId
     * @param orderId
     * @return boolean
     * @author Karl Rouhana
     */
	@DeleteMapping(value = { "/delete_order_item", "/delete_order_item/" })
	public boolean deleteOrderItem(@RequestParam("orderItemId") Long orderItemId,
			@RequestParam("orderId") Long orderId) {

		orderService.deleteItemsToOrder(orderId, orderItemId);
		return orderItemService.deleteOrderItem(orderItemId);

	}

	/**
	 * Convert an order Item to DTO
	 * @param orderItem
	 * @return OrderItemDto
	 * @author Karl Rouhana
	 */
	public static OrderItemDto convertToDTO(OrderItem orderItem) {
		if (orderItem == null)
			throw new IllegalArgumentException("Item not found.");

		ShoppableItemDto itemDto = ShoppableItemController.convertToDTO(orderItem.getItem());

		return new OrderItemDto(orderItem.getQuantity(), itemDto);
	}
}
