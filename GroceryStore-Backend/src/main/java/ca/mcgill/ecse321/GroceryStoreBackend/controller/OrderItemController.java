package ca.mcgill.ecse321.GroceryStoreBackend.controller;

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
import ca.mcgill.ecse321.GroceryStoreBackend.dto.OrderItemDto;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.ShoppableItemDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.service.OrderItemService;


@CrossOrigin(origins = "*")
@RestController
public class OrderItemController {
  
  
  @Autowired
  private OrderItemService orderItemService;

  
  
  @GetMapping(value = { "/view_order_items" })
  public List<OrderItemDto> getAllOrders() {
      return orderItemService.getAllOrderItem().stream().map(orderItem -> convertToDTO(orderItem)).collect(Collectors.toList());
  }

  
  @GetMapping(value = {"/view_order_item/{orderItemId}"})
  public OrderItemDto viewOrderForCustomer(@RequestParam("orderItemId") Long orderItemId
      ) {
      
    
    return convertToDTO(orderItemService.getOrderItemById(orderItemId));
  }
  

  
  @PostMapping(value = {"/create_order_item"})
  public ResponseEntity<?> createOrderItem(@RequestParam("quantity") int quantity,
      @RequestParam("itemName") String itemName, @RequestParam("orderItemId") Long orderItemId
      ,@RequestParam("orderId") Long orderId) {

    
    OrderItem orderItem = null;

    try {
      orderItem = orderItemService.createOrderItem(orderItemId, quantity, itemName,orderId);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(orderItem), HttpStatus.CREATED);
  }
  
  
  @PostMapping(value = {"/update_order_item"})
  public ResponseEntity<?> updateOrderItem(@RequestParam("quantity") int quantity,
      @RequestParam("itemName") String itemName, @RequestParam("orderItemId") Long orderItemId
      ,@RequestParam("orderId") Long orderId) {

    
    OrderItem orderItem = null;

    try {
      orderItem = orderItemService.updateOrderItem(orderItemId,quantity, itemName, orderId);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(orderItem), HttpStatus.CREATED);
  }
  
  
  @PostMapping(value = {"/delete_order_item"})
  public boolean deleteOrderItem( @RequestParam("orderItemId") Long orderItemId) {

      return orderItemService.deleteOrderItem(orderItemId);
  
  }
  
  
  
  public static OrderItemDto convertToDTO(OrderItem orderItem) {
    if (orderItem == null)
      throw new IllegalArgumentException("Item not found.");
    
    ShoppableItemDto itemDto = ShoppableItemController.convertToDTO(orderItem.getItem());
    
    return new OrderItemDto(orderItem.getQuantity(), itemDto);
  }
}
