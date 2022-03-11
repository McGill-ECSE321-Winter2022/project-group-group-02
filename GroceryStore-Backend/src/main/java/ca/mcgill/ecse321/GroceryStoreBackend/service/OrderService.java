package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;

@Service
public class OrderService {


  @Autowired
  OrderRepository orderRepo;
  
  @Autowired
  CustomerRepository customerRepo;
  
  
  
  @Transactional
  public Order createOrder(OrderType aOrderType, OrderStatus aOrderStatus, Date aDate, Time aTime, String email, Long orderId) throws IllegalArgumentException {
      
    if(aOrderType== null) throw new IllegalArgumentException ("Please enter a valid order type. ");
    if(aOrderStatus== null) throw new IllegalArgumentException ("Please enter a valid order status. ");
    if(aDate== null) throw new IllegalArgumentException ("Please enter a valid date. ");
    if(aTime== null) throw new IllegalArgumentException ("Please enter a valid time. ");
    
    
    Customer aCustomer = customerRepo.findByEmail(email);
    if(aCustomer==null) throw new IllegalArgumentException ("Please enter a valid customer. ");

    Order order = orderRepo.findOrderById(orderId);
    if(order != null) throw new IllegalArgumentException ("Order with ID already exists.");
    
      order = new Order(aOrderType, aOrderStatus, aDate, aTime, aCustomer);
     order.setId(orderId);
      orderRepo.save(order);
      return order;
  
  }
  
  @Transactional
  public Order updateOrder(OrderType aOrderType, OrderStatus aOrderStatus, Date aDate, Time aTime, Long orderId) throws IllegalArgumentException {
    
    Order order = orderRepo.findOrderById(orderId);
    if(order== null) throw new IllegalArgumentException ("Please enter a valid order. ");

    if(aOrderType== null) throw new IllegalArgumentException ("Please enter a valid order type. ");
    if(aOrderStatus== null) throw new IllegalArgumentException ("Please enter a valid order status. ");
    if(aDate== null) throw new IllegalArgumentException ("Please enter a valid date. ");
    if(aTime== null) throw new IllegalArgumentException ("Please enter a valid time. ");
    
    
    Customer aCustomer = customerRepo.findByEmail(order.getCustomer().getEmail());
    if(aCustomer== null) throw new IllegalArgumentException ("Please enter a valid customer. ");
    
    System.out.println(aCustomer.getEmail());
    
    List<Order> allOrdersByCustomer = orderRepo.findOrderByCustomer(aCustomer);
    if(allOrdersByCustomer.size() == 0) throw new IllegalArgumentException ("This customer does not have any order. ");
    
    boolean found = false;
    for(Order mock : allOrdersByCustomer) {
      
      if(order.getId()==mock.getId()) {
        found = true;
        break;
      }
      
    }

    if(found == false) throw new IllegalArgumentException ("This order is not associated with this customer. ");

    order.setOrderType(aOrderType);
    order.setOrderStatus(aOrderStatus);
    order.setDate(aDate);
    order.setTime(aTime);
    order.setId(orderId);
    orderRepo.save(order);
    
    return order;
  }
  
  @Transactional
  public boolean cancelOrder(Long orderId) throws IllegalArgumentException {

    Order order = orderRepo.findOrderById(orderId);
    if(order== null) {
      throw new IllegalArgumentException ("Please enter a valid order. ") ;
      
    }
      
    
    
    orderRepo.delete(order);
    order.delete();
    return true;
    
  }
  

  @Transactional
  public List<Order> getAllOrder() {
      
    List<Order> allOrders = toList(orderRepo.findAll());
    
    if(allOrders.size() == 0) throw new IllegalArgumentException ("There's no orders in the system. ");
    
    return allOrders;
  }
  
  
  @Transactional
  public List<Order> getAllOrdersByCustomer(String email) throws IllegalArgumentException {
    
    Customer aCustomer = customerRepo.findByEmail(email);
    if(aCustomer== null) throw new IllegalArgumentException ("Please enter a valid customer. ");

    
    List<Order> allOrdersByCustomer = new ArrayList<>();
   
    
    for (Order order : orderRepo.findOrderByCustomer(aCustomer)) {
      allOrdersByCustomer.add(order);
    }
    return allOrdersByCustomer;
}
  
  @Transactional
  public Order getOrderById(Long orderId)throws IllegalArgumentException {
    
 

    if(orderId== null) throw new IllegalArgumentException ("Please enter a valid order ID. ");
    Order order = orderRepo.findOrderById(orderId);
    
    if(order== null) throw new IllegalArgumentException ("Please enter a valid order by providing a valid order ID. ");

    
    return order;
    
    
  }
  
  
  @Transactional
  public Order setOrderStatus(Long orderId, OrderStatus aOrderStatus) throws IllegalArgumentException {

    Order order = orderRepo.findOrderById(orderId);
    if(order== null) throw new IllegalArgumentException ("Please enter a valid order. ");
    if(aOrderStatus== null) throw new IllegalArgumentException ("Please enter a valid order status. ");
    
    
    order.setOrderStatus(aOrderStatus);
    orderRepo.save(order);
    return order;
    
  }
  
  @Transactional
  public OrderType convertOrderType (String type) {
    
    
    if(type.equals("Delivery")) return OrderType.Delivery;
    if(type.equals("PickUp") || type.equals("Pick Up")|| type.equals("Pick up")) return OrderType.PickUp;
    
    return null;

    
  }
  
  @Transactional
  public OrderStatus convertOrderStatus (String status) {
    
    
    if(status.equals("Confirmed")) return OrderStatus.Confirmed;
    if(status.equals("Preparing")) return OrderStatus.Preparing;
    if(status.equals("Cancelled")) return OrderStatus.Cancelled;
    if(status.equals("Delivering")) return OrderStatus.Delivering;
    if(status.equals("Ready")) return OrderStatus.Ready;
    if(status.equals("Fulfilled")) return OrderStatus.Fulfilled;
    
    return null;

    
  }
  
  
  
  private <T> List<T> toList(Iterable<T> iterable){
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
        resultList.add(t);
    }
    return resultList;
}
  
  
  
 
}
