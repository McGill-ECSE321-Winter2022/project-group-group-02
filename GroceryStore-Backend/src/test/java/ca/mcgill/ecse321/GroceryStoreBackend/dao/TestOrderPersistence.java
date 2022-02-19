package ca.mcgill.ecse321.GroceryStoreBackend.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestOrderPersistence {

  
  @Autowired
  EntityManager entityManager;
  
  @Autowired
  private OrderRepository orderRepository;
  
  @Autowired
  private CustomerRepository customerRepository;
  
  @AfterEach
  public void clearDatabase() {
      orderRepository.deleteAll();
      customerRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadOrder() {
    String name = "testCustomer";
    String email = "testCustomer@mail.com";
    String password = "testPassword";
    String address = "town";
    Customer customer = new Customer();
    customer.setName(name);
    customer.setEmail(email);
    customer.setPassword(password);
    customer.setAddress(address);
    
    customerRepository.save(customer);
    
    
    Long orderId = (long) 1234;
    OrderType orderType = OrderType.Delivery;
    OrderStatus orderStatus = OrderStatus.Confirmed;
    Date date = new Date(0);
    Time time = new Time(0);
    Order order = new Order();
    order.setOrderType(orderType);
    order.setOrderStatus(orderStatus);
    order.setDate(date);
    order.setTime(time);
    order.setCustomer(customer);
    order.setId(orderId);
   
    
    orderRepository.save(order);

    order = null;

    order = orderRepository.findOrderById(orderId);
    assertNotNull(order);
    assertEquals(orderId, order.getId());
  }
  
}
