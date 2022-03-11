package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import ca.mcgill.ecse321.GroceryStoreBackend.dao.CustomerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.OrderRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.ReviewRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Review;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Review.Rating;



@Service
public class ReviewService {



  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private OrderRepository orderRepository;


  @Transactional
  public Review createReview(Rating aRating, String aDescription, String customerEmail,
      Long orderId, Long reviewId) {

    
    if (orderId == null) {
      throw new IllegalArgumentException("Order not found");
    }

    if (aDescription == null) {
      throw new IllegalArgumentException("No description");
    }

    if (aDescription == "") {
      throw new IllegalArgumentException("Description must contain at least 1 character");
    }

    Customer customer = customerRepository.findByEmail(customerEmail);
    if (customer == null) {
      throw new IllegalArgumentException("No customer found");
    }

    Order order = orderRepository.findOrderById(orderId);
    if (order == null) {
      throw new IllegalArgumentException("No order found");
    }
    if (!order.getCustomer().getEmail().equals(customerEmail)) {
      throw new IllegalArgumentException("Order not found for customer");
    }

    Review review = reviewRepository.findByCustomerAndOrder(customer, order);
    if (review != null) {
      throw new IllegalArgumentException("Review already exists for this order");
    }
    
    review = reviewRepository.findReviewById(reviewId);
    if (review != null) {
      throw new IllegalArgumentException("Review with this id already exists");
    }

    review = new Review();
    review.setOrder(order);
    review.setCustomer(customer);
    review.setDescription(aDescription);
    review.setRating(aRating);
    review.setId(reviewId);
    reviewRepository.save(review);
    return review;
  }
  
  
  @Transactional
  public Review updateReview(Long orderId, String newDescription, Rating newRating) {

      Order order = orderRepository.findOrderById(orderId);
      if (order == null) {
        throw new IllegalArgumentException("No order found");
      }

      Review review = reviewRepository.findReviewByOrder(order);


      if(newDescription == null) {
        throw new IllegalArgumentException("No new description");
      }
      
      if(newDescription == "") {
          throw new IllegalArgumentException("New description must contain at least 1 character");
      }

      
      review.setDescription(newDescription);
      review.setRating(newRating);
      reviewRepository.save(review);
      return review;
  }


  @Transactional
  public boolean deleteReview(Long orderId, String customerEmail) {
    Order order = orderRepository.findOrderById(orderId);
    if (order == null) {
      throw new IllegalArgumentException("No order found");
    }
    Customer customer = customerRepository.findByEmail(customerEmail);
    if (customer == null) {
      throw new IllegalArgumentException("No customer found");
    }
    Review review = reviewRepository.findByCustomerAndOrder(customer, order);
    reviewRepository.delete(review);

    return true;
  }

  @Transactional
  public Review getReviewForOrder(Long orderId) {

    Order order = orderRepository.findOrderById(orderId);
    if (order == null) {
      throw new IllegalArgumentException("No order found");
    }

    return reviewRepository.findReviewByOrder(order);
  }

  @Transactional
  public List<Review> getReviewsForCustomer(String customerEmail) {

    Customer customer = customerRepository.findByEmail(customerEmail);
    if (customer == null) {
      throw new IllegalArgumentException("No customer found");
    }

    return toList(reviewRepository.findByCustomer(customer));
  }

  @Transactional
  public List<Review> getAllReviews() {
    return toList(reviewRepository.findAll());
  }


  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }

}


