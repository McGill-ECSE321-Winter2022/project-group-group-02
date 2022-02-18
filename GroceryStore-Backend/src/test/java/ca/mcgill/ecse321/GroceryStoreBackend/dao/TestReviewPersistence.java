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
  
  @AfterEach
  public void clearDatabase() {
      reviewRepository.deleteAll();
      orderRepository.deleteAll();
      customerRepository.deleteAll();
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
      
      
      OrderType orderType = OrderType.Delivery;
      OrderStatus orderStatus = OrderStatus.Confirmed;
      Date date = new Date(0);
      Time time = new Time(0);
      Order order = new Order();
      
      order.setCustomer(customer);
      order.setDate(date);
      order.setTime(time);
      order.setOrderStatus(orderStatus);
      order.setOrderType(orderType);
      
      
      
      String description = "The order was processed quickly";
      Rating rating = Rating.Good;
      Review review = new Review();
      
      review.setRating(rating);
      review.setDescription(description);
      review.setOrder(order);
      review.setCustomer(customer);
      
      customerRepository.save(customer);
      orderRepository.save(order);
      reviewRepository.save(review);

      review = null;
      
      
      review = reviewRepository.findReviewById((long) 1);
      assertNotNull(review);
      assertEquals(description, review.getDescription());
      
  }
  
}
