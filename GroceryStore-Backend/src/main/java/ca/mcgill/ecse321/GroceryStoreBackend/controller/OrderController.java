package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
import ca.mcgill.ecse321.GroceryStoreBackend.dto.CustomerDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.OrderDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.OrderItemDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.service.OrderService;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@GetMapping(value = { "/view_all_orders", "/view_all_orders/" })
	public List<OrderDto> getAllOrders() {
		return orderService.getAllOrder().stream().map(order -> convertToDTO(order)).collect(Collectors.toList());
	}

	@GetMapping(value = { "/view_all_orders_for_customer", "/view_all_orders_for_customer/" })
	public List<OrderDto> viewOrderForCustomer(@RequestParam("email") String email) {

		return orderService.getAllOrdersByCustomer(email).stream().map(order -> convertToDTO(order))
				.collect(Collectors.toList());
	}

	@DeleteMapping(value = { "/delete_order", "/delete_order/" })
	public boolean cancelOrder(@RequestParam("orderId") Long orderId) {

		return orderService.cancelOrder(orderId);
	}

	@PostMapping(value = { "/create_order", "/create_order/" })
	public ResponseEntity<?> createOrder(@RequestParam("orderType") String orderType,
			@RequestParam("email") String email, @RequestParam("orderId") Long orderId) {


		Order order = null;

		try {
			order = orderService.createOrder(orderType, OrderStatus.Confirmed, Date.valueOf(LocalDate.now()),
					Time.valueOf(LocalTime.now()), email, orderId);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(order), HttpStatus.CREATED);
	}

	@PutMapping(value = { "/update_order", "/update_order/" })
	public ResponseEntity<?> updateOrder(@RequestParam("orderStatus") String orderStatus,
			 @RequestParam("orderId") Long orderId) {

		
		Order order = null;

		try {

			order = orderService.updateOrder( orderStatus, orderId);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(convertToDTO(order), HttpStatus.CREATED);
	}

	public static OrderDto convertToDTO(Order order) {
		if (order == null)
			throw new IllegalArgumentException("Order not found.");

		CustomerDto customerDTO = CustomerController.convertToDto(order.getCustomer());
		List<OrderItemDto> orderItemDto = new ArrayList<OrderItemDto>();

		for (OrderItem item : order.getOrderItems()) {

			OrderItemDto itemDto = OrderItemController.convertToDTO(item);
			orderItemDto.add(itemDto);

		}

		return new OrderDto(order.getOrderType(), order.getOrderStatus(), order.getDate(), order.getTime(), customerDTO,
				orderItemDto);
	}

}
