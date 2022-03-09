package ca.mcgill.ecse321.GroceryStoreBackend.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GroceryStoreBackend.dao.*;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Review.Rating;
import ca.mcgill.ecse321.GroceryStoreBackend.service.*;

@CrossOrigin(origins = "*")
@RestController
public class ReviewController {


  @Autowired
  private ReviewService reviewService;


  @PostMapping(value = {"/create_review/"})
  public ResponseEntity<?> createReview(@RequestParam("rating") Rating rating,
      @RequestParam("description") String description,
      @RequestParam("customerEmail") String customerEmail, @RequestParam("orderId") Long orderId) {


    Review review = null;
    try {
      review = reviewService.createReview(rating, description, customerEmail, orderId);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(convertToDto(review), HttpStatus.CREATED);
  }


  @PostMapping(value = {"/update_review/"})
  public ResponseEntity<?> updateReview(@RequestParam("orderId") Long orderId,
      @RequestParam("newDescription") String newDescription,
      @RequestParam("rating") Rating newRating) {


    Review review = null;
    try {
      review = reviewService.updateReview(orderId, newDescription, newRating);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(convertToDto(review), HttpStatus.OK);
  }


  @PostMapping(value = {"/delete_review/"})
  public boolean deleteReview(@RequestParam("orderId") Long orderId,
      @RequestParam("customerEmail") String customerEmail) {


    return reviewService.deleteReview(orderId, customerEmail);
  }

  @GetMapping(value = {"/view_all_reviews"})
  public List<ReviewDto> getAllReviews() {
    return reviewService.getAllReviews().stream().map(review -> convertToDto(review))
        .collect(Collectors.toList());
  }


  @GetMapping(value = {"/view_reviews_for_customer"})
  public List<ReviewDto> getReviewsForCustomer(
      @RequestParam("customerEmail") String customerEmail) {

    return reviewService.getReviewsForCustomer(customerEmail).stream()
        .map(review -> convertToDto(review)).collect(Collectors.toList());
  }

  @GetMapping(value = {"/view_review_for_order"})
  public ReviewDto getReviewForOrder(@RequestParam("orderId") Long orderId) {

    return convertToDto(reviewService.getReviewForOrder(orderId));
  }



  public static ReviewDto convertToDto(Review review) {
    if (review == null)
      throw new IllegalArgumentException("Review not found.");

      //This will be fixed when we implement convertToDto for customer and order
    
    return new ReviewDto(review.getRating(), review.getDescription(),
        CustomerController.convertToDto(review.getCustomer()),
        OrderController.convertToDTO(review.getOrder()));


  }

}
