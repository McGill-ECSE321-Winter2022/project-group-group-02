package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;



@ExtendWith(MockitoExtension.class)
public class TestOrderService {
  
  @Mock
  private OrderRepository orderRepo;
  @InjectMocks
  private OrderService orderService;
  @InjectMocks
  private CustomerService customerService;
  
  
  @Test
  public void testCreateOrder() {
    String username = "nameTest";
    String password = "passwordTest1";
    String email = "customer@localTown.com";
    String address = "Local Town";
    Customer customer = null;
    customer = customerService.createCustomer(email, password, username, address);
    
    OrderType aOrderType = OrderType.PickUp;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-03-10");
    Time time = Time.valueOf("08:00:00");
    
    Order order = null;
    
    try {
       order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email);
      
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(order);
    assertEquals(aOrderType, order.getOrderType());
    assertEquals(aOrderStatus, order.getOrderStatus());
    assertEquals(time, order.getTime());
    assertEquals(date, order.getDate());
    assertEquals(customer, order.getCustomer());

    
  }

  
  
  

}
