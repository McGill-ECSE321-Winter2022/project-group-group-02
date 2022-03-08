package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderItemRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;

@Service
public class OrderItemService {

  @Autowired
  OrderItemRepository orderItemRepo;
  
  
  @Transactional
  public OrderItem createOrderItem(int quantity, String itemName) throws IllegalArgumentException {
      
    if(quantity < 0) throw new IllegalArgumentException ("Please enter a valid quantity. ");
    
    ShoppableItem item = (ShoppableItem) ShoppableItem.getWithName(itemName);
    
    if(item.equals(null)) throw new IllegalArgumentException ("Please enter a valid item. ");
   
    
      OrderItem orderItem = new OrderItem();
      orderItem.setQuantity(quantity);
      orderItem.setItem(item); 
      
      orderItemRepo.save(orderItem);
      return orderItem;
  
  }
  
  @Transactional
  public OrderItem updateOrderItem(OrderItem orderItem, int quantity, String itemName) throws IllegalArgumentException {
    
    if(quantity < 0) throw new IllegalArgumentException ("Please enter a valid quantity. ");
    
    ShoppableItem item = (ShoppableItem) ShoppableItem.getWithName(itemName);
    
    if(item.equals(null)) throw new IllegalArgumentException ("Please enter a valid item. ");
   
    
      orderItem.setQuantity(quantity);
      orderItem.setItem(item); 
      
      orderItemRepo.save(orderItem);
      return orderItem;
  }
  
  @Transactional
  public void deleteOrderItem(OrderItem orderItem) throws IllegalArgumentException {

    if(orderItemRepo.equals(null)) throw new IllegalArgumentException ("Please enter a valid order Item. ");
    
    orderItemRepo.delete(orderItem);
    orderItem.delete();
    
  }
  
  
  
  @Transactional
  public List<OrderItem> getAllOrderItem() {
      return toList(orderItemRepo.findAll());
  }
  
  
  @Transactional
  public List<OrderItem> getAllOrdersByOrder(Order order) throws IllegalArgumentException {
    
    if(order.equals(null)) throw new IllegalArgumentException ("Please enter a valid order. ");

    
    List<OrderItem> allOrderItemsByOrder = new ArrayList<>();
   
    
    for (OrderItem orderItem : orderItemRepo.findOrderItemByOrder(order)) {
      allOrderItemsByOrder.add(orderItem);
    }
    return allOrderItemsByOrder;
}
  
  
  
  
  
  
  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
  
  
}
