package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;

@Service
public class OrderService {


  @Autowired
  OrderRepository orderRepo;
  
  
  
  @Transactional
  public Order createOrder(OrderType aOrderType, OrderStatus aOrderStatus, Date aDate, Time aTime, String email) throws IllegalArgumentException {
      
    if(aOrderType.equals(null)) throw new IllegalArgumentException ("Please enter a valid order type. ");
    if(aOrderStatus.equals(null)) throw new IllegalArgumentException ("Please enter a valid order status. ");
    if(aDate.equals(null)) throw new IllegalArgumentException ("Please enter a valid date. ");
    if(aTime.equals(null)) throw new IllegalArgumentException ("Please enter a valid time. ");
    
    
    Customer aCustomer = (Customer) Customer.getWithEmail(email);
    if(aCustomer.equals(null)) throw new IllegalArgumentException ("Please enter a valid customer. ");

    
      Order order = new Order(aOrderType, aOrderStatus, aDate, aTime, aCustomer);
      orderRepo.save(order);
      return order;
  
  }
  
  @Transactional
  public Order updateOrder(Order order, OrderType aOrderType, OrderStatus aOrderStatus, Date aDate, Time aTime, String email) throws IllegalArgumentException {
    
    if(order.equals(null)) throw new IllegalArgumentException ("Please enter a valid order. ");
    if(aOrderType.equals(null)) throw new IllegalArgumentException ("Please enter a valid order type. ");
    if(aOrderStatus.equals(null)) throw new IllegalArgumentException ("Please enter a valid order status. ");
    if(aDate.equals(null)) throw new IllegalArgumentException ("Please enter a valid date. ");
    if(aTime.equals(null)) throw new IllegalArgumentException ("Please enter a valid time. ");
    
    Customer aCustomer = (Customer) Customer.getWithEmail(email);

    if(aCustomer.equals(null)) throw new IllegalArgumentException ("Please enter a valid customer. ");

    if(orderRepo.findOrderById(order.getId()).equals(null)) throw new IllegalArgumentException ("Please enter a valid order. ");

    order.setOrderType(aOrderType);
    order.setOrderStatus(aOrderStatus);
    order.setDate(aDate);
    order.setTime(aTime);
    order.setCustomer(aCustomer);
    
    orderRepo.save(order);
    
    return order;
  }
  
  @Transactional
  public void cancelOrder(Order order) throws IllegalArgumentException {

    if(order.equals(null)) throw new IllegalArgumentException ("Please enter a valid order. ");
    
    orderRepo.delete(order);
    order.delete();
    
  }
  

  @Transactional
  public List<Order> getAllOrder() {
      return toList(orderRepo.findAll());
  }
  
  
  @Transactional
  public List<Order> getAllOrdersByCustomer(String email) throws IllegalArgumentException {
    
    Customer aCustomer = (Customer) Customer.getWithEmail(email);
    if(aCustomer.equals(null)) throw new IllegalArgumentException ("Please enter a valid customer. ");

    
    List<Order> allOrdersByCustomer = new ArrayList<>();
   
    
    for (Order order : orderRepo.findOrderByCustomer(aCustomer)) {
      allOrdersByCustomer.add(order);
    }
    return allOrdersByCustomer;
}
  
  
  @Transactional
  public Order setOrderStatus(Order order, OrderStatus aOrderStatus) throws IllegalArgumentException {

    if(order.equals(null)) throw new IllegalArgumentException ("Please enter a valid order. ");
    if(aOrderStatus.equals(null)) throw new IllegalArgumentException ("Please enter a valid order status. ");
    
    
    order.setOrderStatus(aOrderStatus);
    orderRepo.save(order);
    return order;
    
  }
  
  
  
  
  
  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
  
  
  
 
}
