package ca.mcgill.ecse321.GroceryStoreBackend.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
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
import ca.mcgill.ecse321.GroceryStoreBackend.model.Review.Rating;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestReviewPersistence {

  
  @Autowired
  EntityManager entityManager;
  
  @Autowired
  private ReviewRepository reviewRepository;
  
  @Autowired
  private OrderRepository orderRepository;
  
  @Autowired
  private CustomerRepository customerRepository;
  
  @Autowired
  private ShoppableItemRepository shoppableItemRepository;
  
  @Autowired
  private OrderItemRepository orderItemRepository;
  
  @AfterEach
  public void clearDatabase() {
    
      reviewRepository.deleteAll();
      orderRepository.deleteAll();
      orderItemRepository.deleteAll();
      customerRepository.deleteAll();
      shoppableItemRepository.deleteAll();
  }
  
  @Test
  public void testPersistAndLoadReview() {
      
    
      String name = "testCustomer";
      String email = "testCustomer@mail.com";
      String password = "testPassword";
      String address = "town";
      Customer customer = new Customer();
      customer.setAddress(address);
      customer.setEmail(email);
      customer.setPassword(password);
      customer.setName(name);
      
      customerRepository.save(customer);

      
      String itemName = "Milk";
      double price = 4.65;
      int quantityAvailable = 10;
      ShoppableItem shoppableItem = new ShoppableItem ();
      shoppableItem.setName(itemName);
      shoppableItem.setPrice(price);
      shoppableItem.setQuantityAvailable(quantityAvailable);
      shoppableItemRepository.save(shoppableItem);
      
      
      Long orderItemId = (long) 1234;
      int quantityWanted = 2;
      OrderItem orderItem = new OrderItem();
      
      orderItem.setQuantity(quantityWanted);
      orderItem.setItem(shoppableItem);
      orderItem.setId(orderItemId);
      orderItemRepository.save(orderItem);
      
      
      OrderType orderType = OrderType.Delivery;
      OrderStatus orderStatus = OrderStatus.Confirmed;
      Date date = new Date(0);
      Time time = new Time(0);
      Long orderId = (long) 21;
      Order order = new Order();
      
      order.setCustomer(customer);
      order.setDate(date);
      order.setTime(time);
      order.setOrderStatus(orderStatus);
      order.setOrderType(orderType);
      order.setId(orderId);
      order.addOrderItem(orderItem);
      
      orderRepository.save(order);

      
      Long reviewId = (long) 21;
      String description = "The order was processed quickly";
      Rating rating = Rating.Good;
      Review review = new Review();
      
      review.setRating(rating);
      review.setDescription(description);
      review.setId(reviewId);
      review.setOrder(order);
      review.setCustomer(customer);
      
      
      reviewRepository.save(review);

      review = null;
      
      
      review = reviewRepository.findReviewById(reviewId);
      assertNotNull(review);
      assertEquals(description, review.getDescription());
      
  }
  
}
