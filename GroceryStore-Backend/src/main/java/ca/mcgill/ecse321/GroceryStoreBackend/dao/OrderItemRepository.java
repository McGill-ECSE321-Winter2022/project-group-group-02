package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;


public interface OrderItemRepository extends CrudRepository<OrderItem, Integer>  {

  
//  boolean existsByOrderAndShoppableItem(Order orderId, ShoppableItem itemName);
//
//  OrderItem findByOrderAndShoppableItem(Order orderId, ShoppableItem itemName);
  
  OrderItem findOrderItemById(Integer orderItemId);
  
  
}
