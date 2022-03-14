package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321.GroceryStoreBackend.dto.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Review.Rating;
import ca.mcgill.ecse321.GroceryStoreBackend.service.*;

@CrossOrigin(origins = "*")
@RestController
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	
	/**
     * @author Matthieu Hakim
     * Creates a review
     * @param aRating
     * @param aDescription
     * @param customerEmail
     * @param orderId
     * @param reviewId
     * @return ReviewDto
     */
	@PostMapping(value = { "/create_review/", "/create_review" })
	public ResponseEntity<?> createReview(@RequestParam("rating") Rating rating,
			@RequestParam("description") String description, @RequestParam("customerEmail") String customerEmail,
			@RequestParam("orderId") Long orderId, @RequestParam("reviewId") Long reviewId) {

		Review review = null;
		try {
			review = reviewService.createReview(rating, description, customerEmail, orderId, reviewId);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(convertToDto(review), HttpStatus.CREATED);
	}

	/**
     * @author Matthieu Hakim
     * Updates a review
     * @param orderId
     * @param newDescription
     * @param newRating
     * @return ReviewDto
     */
	@PutMapping(value = { "/update_review/", "/update_review" })
	public ResponseEntity<?> updateReview(@RequestParam("orderId") Long orderId,
			@RequestParam("newDescription") String newDescription, @RequestParam("rating") Rating newRating) {

		Review review = null;
		try {
			review = reviewService.updateReview(orderId, newDescription, newRating);
		} catch (IllegalArgumentException exception) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(convertToDto(review), HttpStatus.OK);
	}

	/**
     * @author Matthieu Hakim
     * Deletes the review of a specific order
     * @param orderId
     * @return true if review has been deleted
     */
	@DeleteMapping(value = { "/delete_review/", "/delete_review" })
	public boolean deleteReview(@RequestParam("orderId") Long orderId) {

		return reviewService.deleteReview(orderId);
	}

	
	/**
     * @author Matthieu Hakim
     * Gets the all reviews in the system
     * @return List<ReviewDto>
     */
	@GetMapping(value = { "/view_all_reviews/", "/view_all_reviews" })
	public List<ReviewDto> getAllReviews() {
		return reviewService.getAllReviews().stream().map(review -> convertToDto(review)).collect(Collectors.toList());
	}

	
	/**
     * @author Matthieu Hakim
     * Gets the all reviews of a specific customer
     * @param customerEmail
     * @return List<ReviewDto>
     */
	@GetMapping(value = { "/view_reviews_for_customer/", "/view_reviews_for_customer" })
	public List<ReviewDto> getReviewsForCustomer(@RequestParam("customerEmail") String customerEmail) {

		return reviewService.getReviewsForCustomer(customerEmail).stream().map(review -> convertToDto(review))
				.collect(Collectors.toList());
	}

	
	/**
     * @author Matthieu Hakim
     * Gets the review of a specific order
     * @param orderId
     * @return ReviewDto
     */
	@GetMapping(value = { "/view_review_for_order/", "/view_review_for_order" })
	public ReviewDto getReviewForOrder(@RequestParam("orderId") Long orderId) {

		return convertToDto(reviewService.getReviewForOrder(orderId));
	}

	
	/**
     * @author Matthieu Hakim
     * Converts Review object to ReviewDto
     * @return ReviewDto
     */
	public static ReviewDto convertToDto(Review review) {
		if (review == null)
			throw new IllegalArgumentException("Review not found.");

		return new ReviewDto(review.getRating(), review.getDescription(),
				CustomerController.convertToDto(review.getCustomer()), OrderController.convertToDTO(review.getOrder()));

	}

}
