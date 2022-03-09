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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.CustomerDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.OrderDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.OrderItemDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;
import ca.mcgill.ecse321.GroceryStoreBackend.service.OrderService;

@CrossOrigin(origins = "*")
@RestController
public class OrderController {
  
  
  @Autowired
  private OrderService orderService;

  
  @GetMapping(value = { "/view_orders" })
  public List<OrderDto> getAllOrders() {
      return orderService.getAllOrder().stream().map(order -> convertToDTO(order)).collect(Collectors.toList());
  }

  
  @GetMapping(value = {"/view_order/{email}"})
  public List<OrderDto> viewOrderForCustomer(@RequestParam("email") String email) {
      
    
    return orderService.getAllOrdersByCustomer(email).stream().map(order -> convertToDTO(order)).collect(Collectors.toList());
  }
  
  
  @PostMapping(value = {"/cancel_order/"})
  public boolean cancelOrder(@RequestParam("orderId") Long orderId,
      @RequestParam("customerEmail") String customerEmail) {
    
    return orderService.cancelOrder(orderService.getOrderByCustomerAndId(customerEmail, orderId));
  }
  
  //OrderType aOrderType, OrderStatus aOrderStatus, Date aDate, Time aTime, String email
  
  
  @PostMapping(value = {"/create_order"})
  public ResponseEntity<?> createOrder(@RequestParam("orderType") OrderType orderType,
      @RequestParam("email") String email) {

    
    Order order = null;

    try {
      order = orderService.createOrder(orderType, OrderStatus.Confirmed, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), email);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(order), HttpStatus.CREATED);
  }
  
  
  @PostMapping(value = {"/update_order"})
  public ResponseEntity<?> updateOrder(@RequestParam("orderType") OrderType orderType, 
      @RequestParam("email") String email,  @RequestParam("id") String id) {

    Long orderId = Long.parseLong(id);
    
    Order oldOrder = orderService.getOrderByCustomerAndId(email, orderId);
    Order order = null;

    try {
      order = orderService.updateOrder(oldOrder, orderType, OrderStatus.Confirmed, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()), email);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(order), HttpStatus.CREATED);
  }
  
  
  @PostMapping(value = {"/update_order_status"})
  public ResponseEntity<?> updateOrderStatus(@RequestParam("orderStatus") OrderStatus orderStatus, 
      @RequestParam("email") String email,  @RequestParam("id") String id) {

    Long orderId = Long.parseLong(id);
    
    Order oldOrder = orderService.getOrderByCustomerAndId(email, orderId);
    Order order = null;

    try {
      order = orderService.setOrderStatus(oldOrder, orderStatus);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(order), HttpStatus.CREATED);
  }
  
  
  
  public static OrderDto convertToDTO(Order order) {
    if (order == null)
      throw new IllegalArgumentException("Order not found.");
    
    CustomerDto customerDTO = GroceryStoreBackendController.convertToDto(order.getCustomer());
    List<OrderItemDto> orderItemDto = new ArrayList<OrderItemDto>();
    
    for(OrderItem item : order.getOrderItems()) {
      
      OrderItemDto itemDto = OrderItemController.convertToDTO(item);
      orderItemDto.add(itemDto);
      
    }
    
        return new OrderDto(order.getOrderType(), order.getOrderStatus(), order.getDate(), order.getTime(), customerDTO, orderItemDto);
  }

}