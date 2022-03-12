package ca.mcgill.ecse321.GroceryStoreBackend.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.sql.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;



@ExtendWith(MockitoExtension.class)
public class TestOrderService {
  
  @Mock
  private OrderRepository orderRepo;
  @Mock
  private CustomerRepository customerRepo;
  
  @InjectMocks
  private OrderService orderService;
  @InjectMocks
  private CustomerService customerService;
  

  
  private static final String CUSTOMER_EMAIL = "theBestValuableCustomer@lollar.com";
  private static final String CUSTOMER_NAME = "Bank";
  private static final String CUSTOMER_ADDRESS = "Beirut, the land of opportinities";
  private static final String CUSTOMER_PASSWORD = "Audi2019";

  
  private static final Long ORDER_ID = 1234567L;
  private static final OrderType ORDER_TYPE = OrderType.PickUp;
  private static final OrderStatus ORDER_STATUS = OrderStatus.Confirmed;
  private static final Date ORDER_DATE = Date.valueOf("2022-03-10");
  private static final Time ORDER_TIME = Time.valueOf("10:09:00");;

  
  @BeforeEach
  public void setMockOutput() {


    
    
    lenient().when(orderRepo.findOrderById(any(Long.class)))
    .thenAnswer((InvocationOnMock invocation) -> {
      if (invocation.getArgument(0).equals(ORDER_ID)) {

        Customer customer = new Customer();
        customer.setEmail(CUSTOMER_EMAIL);
        customer.setName(CUSTOMER_NAME);
        customer.setAddress(CUSTOMER_ADDRESS);
        customer.setPassword(CUSTOMER_PASSWORD);

        Order order = new Order();
        order.setCustomer(customer);
        order.setId(ORDER_ID);
        order.setOrderStatus(ORDER_STATUS);
        order.setOrderType(ORDER_TYPE);
        order.setDate(ORDER_DATE);
        order.setTime(ORDER_TIME);


        return order;
      } else {
        return null;
      }

    });
    
    
    
    lenient().when(customerRepo.findByEmail(anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
          if (invocation.getArgument(0).equals(CUSTOMER_EMAIL)) {
            Customer customer = new Customer();
            customer.setEmail(CUSTOMER_EMAIL);
            customer.setName(CUSTOMER_NAME);
            customer.setAddress(CUSTOMER_ADDRESS);
            customer.setPassword(CUSTOMER_PASSWORD);
            return customer;
          } else {
            return null;
          }

        });

   
   
    Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> {
      return invocation.getArgument(0);
    };

    lenient().when(customerRepo.save(any(Customer.class))).thenAnswer(returnParameterAsAnswer);
    lenient().when(orderRepo.save(any(Order.class))).thenAnswer(returnParameterAsAnswer);


  
  }
  
  /**
   * tests for create order
   */
  
  @Test
  public void testCreateOrder() {
    String name = "nameTest";
    String password = "passwordTest1";
    String email = "customer@localTown.com";
    String address = "1325 Depaneur Kiwi";
    
    Customer customer = customerService.createCustomer(email, password, name, address);
    lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

    Long orderId = 558L;
    OrderType aOrderType = OrderType.PickUp;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-03-10");
    Time time = Time.valueOf("08:00:00");
    
    Order order = null;
    
    try {
       order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);
      
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(order);
    assertEquals(aOrderType, order.getOrderType());
    assertEquals(aOrderStatus, order.getOrderStatus());
    assertEquals(time, order.getTime());
    assertEquals(date, order.getDate());
    assertEquals(customer, order.getCustomer());
    assertEquals(orderId, order.getId());

    
  }

  
  @Test
  public void testCreateOrderNullCustomer() {
    
    String email = "noOneHas@thatEmail.com";

    Long orderId = 557L;
    OrderType aOrderType = OrderType.PickUp;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-01-10");
    Time time = Time.valueOf("06:00:00");
    
    Order order = null;
    String error = null;
    
    try {
      order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);
      
    } catch (IllegalArgumentException e) {
          
      error = e.getMessage();
    }
    assertNull(order);
    assertEquals("Please enter a valid customer. ",error);
    
   
    
  }
  
  @Test
  public void testCreateOrderNullOrderType() {
    
    String name = "Hassan Diab";
    String password = "passwordTest1";
    String email = "Hassoun@localTown.com";
    String address = "1325 Depaneur Jamhouri";
    
    Customer customer = customerService.createCustomer(email, password, name, address);
    lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

    Long orderId = 537L;
    OrderType aOrderType = null;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-03-15");
    Time time = Time.valueOf("08:45:38");
    
    Order order = null;
    String error = null;
    
    try {
      order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);
      
    } catch (IllegalArgumentException e) {
          
      error = e.getMessage();
    }
    assertNull(order);
    assertEquals("Please enter a valid order type. ",error);
    
   
    
  }
  
  
  @Test
  public void testCreateOrderNullOrderStatus() {
    
    String name = "Samir Abdel Hak";
    String password = "passwordTest1";
    String email = "Samir@localTown.com";
    String address = "8564 Jamhouri Daou Kiwi";
    
    Customer customer = customerService.createCustomer(email, password, name, address);
    lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

    Long orderId = 5874L;
    OrderType aOrderType = OrderType.PickUp;
    OrderStatus aOrderStatus = null;
    Date date = Date.valueOf("2022-04-10");
    Time time = Time.valueOf("08:04:00");
    
    Order order = null;
    String error = null;
    
    try {
      order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);
      
    } catch (IllegalArgumentException e) {
          
      error = e.getMessage();
    }
    assertNull(order);
    assertEquals("Please enter a valid order status. ",error);
    
   
    
  }
  @Test
  public void testCreateOrderNullTime() {
    
    String name = "Louay";
    String password = "passwordTest1";
    String email = "Louay@localTown.com";
    String address = "Rue Mar Takla Depaneur Kiwi";
    
    Customer customer = customerService.createCustomer(email, password, name, address);
    lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

    Long orderId = 55894L;
    OrderType aOrderType = OrderType.PickUp;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-03-10");
    Time time =null;
    
    Order order = null;
    String error = null;
    
    try {
      order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);
      
    } catch (IllegalArgumentException e) {
          
      error = e.getMessage();
    }
    assertNull(order);
    assertEquals("Please enter a valid time. ",error);
    
    
  }
  @Test
  public void testCreateOrderNullDate() {
    
    String name = "Mark";
    String password = "passwordTest1";
    String email = "mark@localTown.com";
    String address = "Rue Salim Sleim";
    
    Customer customer = customerService.createCustomer(email, password, name, address);
    lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

    Long orderId = 5536L;
    OrderType aOrderType = OrderType.PickUp;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = null;
    Time time =Time.valueOf("14:45:00");
    
    Order order = null;
    String error = null;
    
    try {
      order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);
      
    } catch (IllegalArgumentException e) {
          
      error = e.getMessage();
    }
    assertNull(order);
    assertEquals("Please enter a valid date. ",error);
    
    
  }
  
  
  @Test
  public void testCreateOrderwithOrderIdAlreadyTaken() {
    String name = "Karl";
    String password = "passwordTest1";
    String email = "karl@localTown.com";
    String address = "1325 Depaneur Pa Kiwi";
    
    Customer customer = customerService.createCustomer(email, password, name, address);
    lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

    OrderType aOrderType = OrderType.PickUp;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-05-10");
    Time time = Time.valueOf("08:54:00");
    
    Order order = null;
    String error = null;
    try {
       order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, ORDER_ID);
      
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    
    }
    assertNull(order);
    assertEquals(error,"Order with ID already exists.");

    
  }

  
  
  
  
  
  
  //-----------------------------------------------------------------------------------------------------------------------------------//
  
  
  
  
  /**
   * tests for update order
   */
  
  @Test
  public void testUpdateOrder() {
  
    OrderType aOrderType = OrderType.Delivery;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-02-10");
    Time time = Time.valueOf("07:58:15");
    
    Order order = null;
    
    try {
       order = orderService.updateOrder(aOrderType, aOrderStatus, date, time, ORDER_ID);
      
    } catch (IllegalArgumentException e) {
      fail();
    }
    assertNotNull(order);
  
    assertEquals(aOrderType, order.getOrderType());
    assertEquals(aOrderStatus, order.getOrderStatus());
    assertEquals(time, order.getTime());
    assertEquals(date, order.getDate());
    assertEquals(CUSTOMER_EMAIL, order.getCustomer().getEmail());
    assertEquals(ORDER_ID, order.getId());
    
  }
  
  @Test
  public void testUpdateOrderNullType() {
  
    OrderType aOrderType = null;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-02-10");
    Time time = Time.valueOf("07:58:15");
    
    Order order = null;
    String error = null;
    
    try {
       order = orderService.updateOrder(aOrderType, aOrderStatus, date, time, ORDER_ID);
      
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(order);
  
    assertEquals(error, "Please enter a valid order type. ");
  
    
  }
  
  @Test
  public void testUpdateOrderNullStatus() {
  
    OrderType aOrderType = OrderType.Delivery;
    OrderStatus aOrderStatus = null;
    Date date = Date.valueOf("2022-02-10");
    Time time = Time.valueOf("07:58:15");
    
    Order order = null;
    String error = null;
    try {
       order = orderService.updateOrder(aOrderType, aOrderStatus, date, time, ORDER_ID);
      
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(order);
  
    assertEquals(error, "Please enter a valid order status. ");
    
  }
  
  @Test
  public void testUpdateOrderNullDate() {
  
    OrderType aOrderType = OrderType.Delivery;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = null;
    Time time = Time.valueOf("07:58:15");
    
    Order order = null;
    String error = null;
    
    try {
       order = orderService.updateOrder(aOrderType, aOrderStatus, date, time, ORDER_ID);
      
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }
    assertNull(order);
  
    assertEquals(error, "Please enter a valid date. ");
  
    
  }
  @Test
  public void testUpdateOrderNullTime() {
  
    OrderType aOrderType = OrderType.Delivery;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-02-10");
    Time time = null;
    
    Order order = null;
    String error = null;

    try {
      order = orderService.updateOrder(aOrderType, aOrderStatus, date, time, ORDER_ID);
     
   } catch (IllegalArgumentException e) {
     error = e.getMessage();
   }
   assertNull(order);
 
   assertEquals(error, "Please enter a valid time. ");
  }
  

//-----------------------------------------------------------------------------------------------------------------------------------//

  /**
   * 
   * 
   * 
   * 
   */
  @Test
  public void testCancelOrder() {
    
    String name = "nameTest";
    String password = "passwordTest1";
    String email = "customer@localTown.com";
    String address = "1325 Depaneur Kiwi";
    
    Customer customer = customerService.createCustomer(email, password, name, address);
    lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

    Long orderId = 558L;
    OrderType aOrderType = OrderType.PickUp;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-03-10");
    Time time = Time.valueOf("08:00:00");
    
    Order order = null;
    order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email,orderId);
    lenient().when(orderRepo.findOrderById(orderId)).thenReturn(order);

    boolean deleted = false;
   
    try {
       deleted = orderService.cancelOrder(orderId);
      
    } catch (IllegalArgumentException e) {
      fail();
    }

    assertTrue(deleted);
    
    
    
    
  }
  
  @Test
  public void testCancelOrderWithInvalidOrder() {

    Long orderId = 558L;


    String error=null;
    
   
    try {
      orderService.cancelOrder(orderId);
      
    } catch (IllegalArgumentException e) {
      error = e.getMessage();
    }

    assertEquals(error, "Please enter a valid order. ");
    
    
    
    
  }
  //-----------------------------------------------------------------------------------------------------------------------------------//

  

  
  @Test
  public void testGetOrderById() {
    
    Order order = null;
    
    try {
      order = orderService.getOrderById(ORDER_ID);
      
    } catch (IllegalArgumentException e) {

      fail();
    }
    
    assertEquals(order.getId(), ORDER_ID);
    assertNotNull(order);
    
  }
  @Test
  public void testGetOrderByIdButInvalid() {
    
    Order order = null;
    Long fakeId = 98L;
    String error = null;
    try {
      order = orderService.getOrderById(fakeId);
      
    } catch (IllegalArgumentException e) {

      error = e.getMessage();
    }
    
    assertEquals(error, "Please enter a valid order by providing a valid order ID. ");
    assertNull(order);
    
  }
  
  
  @Test
  public void testSetOrderStatus() {
    
    String name = "nameTest";
    String password = "passwordTest1";
    String email = "customer@localTown.com";
    String address = "1325 Depaneur Kiwi";
    
    Customer customer = customerService.createCustomer(email, password, name, address);
    lenient().when(customerRepo.findByEmail(email)).thenReturn(customer);

    Long orderId = 558L;
    OrderType aOrderType = OrderType.PickUp;
    OrderStatus aOrderStatus = OrderStatus.Confirmed;
    Date date = Date.valueOf("2022-03-10");
    Time time = Time.valueOf("08:00:00");
    
    Order order = orderService.createOrder(aOrderType, aOrderStatus, date, time, email, orderId);
    lenient().when(orderRepo.findOrderById(orderId)).thenReturn(order);
 
    
    try {
      
      orderService.setOrderStatus(orderId, OrderStatus.Fulfilled);
      
    }catch(IllegalArgumentException e) {
      
      fail();
    }
   order = orderService.getOrderById(orderId);
    
    
    assertEquals(OrderStatus.Fulfilled, order.getOrderStatus());
    
    
    
  }
  
  
  //-----------------------------------------------------------------------------------------------------------------------------------//

  
  @Test
  public void testConverToOrderStatus() {

    String confirmed, preparing, cancelled, delivering, ready, fulfilled;
    String wontWork = "Not valid";
    confirmed = "Confirmed";
    preparing = "Preparing";
    cancelled = "Cancelled";
    delivering = "Delivering";
    ready = "Ready";
    fulfilled = "Fulfilled";
    
    OrderStatus a = null;
    OrderStatus b = null;
    OrderStatus c = null;
    OrderStatus d = null;
    OrderStatus e = null;
    OrderStatus f = null;
    OrderStatus g = null;
   
    try {
       a = orderService.convertOrderStatus(confirmed);
       b = orderService.convertOrderStatus(preparing);
       c =  orderService.convertOrderStatus(cancelled);
       d = orderService.convertOrderStatus(delivering);
       e = orderService.convertOrderStatus(ready);
       f = orderService.convertOrderStatus(fulfilled);
       g = orderService.convertOrderStatus(wontWork);

      
    } catch (IllegalArgumentException z) {
    
        fail();
    }
    
    assertEquals(OrderStatus.Confirmed,a );
    assertEquals(OrderStatus.Preparing,b );
    assertEquals(OrderStatus.Cancelled,c);
    assertEquals(OrderStatus.Delivering,d );
    assertEquals(OrderStatus.Ready,e );
    assertEquals(OrderStatus.Fulfilled,f );
    assertNull(g);

    
  }
  
  //-----------------------------------------------------------------------------------------------------------------------------------//

  @Test
  public void testConvertToOrderType() {
    
    String delivery = "Delivery";
    String pickUp = "PickUp";
    String failed = "unknown";
    OrderType a = null;
    OrderType b = null;
    OrderType c = null;

    try {
      
      a = orderService.convertOrderType(delivery);
      b = orderService.convertOrderType(pickUp);
      c = orderService.convertOrderType(failed);
      
    } catch (IllegalArgumentException e) {
      
      fail();
  }
     
    assertEquals(OrderType.Delivery,a );
    assertEquals(OrderType.PickUp,b );
    assertNull(c);
    
  }
  //-----------------------------------------------------------------------------------------------------------------------------------//

 

}
