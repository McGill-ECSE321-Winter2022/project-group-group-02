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

	/**
	 * @author Matthieu Hakim
	 *         Creates a review
	 * @param aRating
	 * @param aDescription
	 * @param customerEmail
	 * @param orderId
	 * @param reviewId
	 * @return Review
	 */
	@Transactional
	public Review createReview(Rating aRating, String aDescription, String customerEmail, Long orderId) {

		if (aDescription == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}

		if (aDescription.isBlank()) {
			throw new IllegalArgumentException("Description cannot be empty");
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

		review = new Review();
		review.setOrder(order);
		review.setCustomer(customer);
		review.setDescription(aDescription);
		review.setRating(aRating);
		// review.setId(reviewId);
		Review newReview = reviewRepository.save(review);
		return newReview;
	}

	/**
	 * @author Matthieu Hakim
	 *         Updates a review
	 * @param orderId
	 * @param newDescription
	 * @param newRating
	 * @return Review
	 */
	@Transactional
	public Review updateReview(Long orderId, String newDescription, Rating newRating) {

		Order order = orderRepository.findOrderById(orderId);
		if (order == null) {
			throw new IllegalArgumentException("No order found");
		}

		Review review = reviewRepository.findReviewByOrder(order);
		if (review == null) {
			throw new IllegalArgumentException("Cannot update non existing review");
		}

		if (newDescription == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}

		if (newDescription.isBlank()) {
			throw new IllegalArgumentException("Description cannot be empty");
		}

		review.setDescription(newDescription);
		review.setRating(newRating);
		reviewRepository.save(review);
		return review;
	}

	/**
	 * @author Matthieu Hakim
	 *         Deletes the review of a specific order
	 * @param orderId
	 * @return true if review has been deleted
	 */
	@Transactional
	public boolean deleteReview(Long orderId) {
		Order order = orderRepository.findOrderById(orderId);
		if (order == null) {
			throw new IllegalArgumentException("No order found");
		}

		Review review = reviewRepository.findReviewByOrder(order);
		reviewRepository.delete(review);

		return true;
	}

	/**
	 * @author Matthieu Hakim
	 *         Gets the review of a specific order
	 * @param orderId
	 * @return Review
	 */
	@Transactional
	public Review getReviewForOrder(Long orderId) {

		Order order = orderRepository.findOrderById(orderId);
		if (order == null) {
			throw new IllegalArgumentException("No order found");
		}

		return reviewRepository.findReviewByOrder(order);
	}

	/**
	 * @author Matthieu Hakim
	 *         Gets the all reviews of a specific customer
	 * @param customerEmail
	 * @return List<Review>
	 */
	@Transactional
	public List<Review> getReviewsForCustomer(String customerEmail) {

		Customer customer = customerRepository.findByEmail(customerEmail);
		if (customer == null) {
			throw new IllegalArgumentException("No customer found");
		}

		return toList(reviewRepository.findByCustomer(customer));
	}

	/**
	 * @author Matthieu Hakim
	 *         Gets the all reviews in the system
	 * @return List<Review>
	 */
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
