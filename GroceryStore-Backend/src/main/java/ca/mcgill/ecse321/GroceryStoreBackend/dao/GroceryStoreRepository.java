package ca.mcgill.ecse321.GroceryStoreBackend.dao;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule.DayOfWeek;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Review.Rating;

@Repository
public class GroceryStoreRepository {

	@Autowired
	EntityManager entityManager;


	
	/** Creates, persists and returns a Customer instance.
	 * 
	 * @param email
	 * @param password
	 * @param name
	 * @param address
	 * @return c
	 */
	@Transactional
	public Customer createCustomer(String email, String password, String name, String address) {
		Customer c = new Customer(email, password, name, address); // Creation of Customer instance
		entityManager.persist(c); // Persitst Customer instance
		return c;
	}

	
	/** Return the Customer associated with the given email.
	 * 
	 * @param email
	 * @return c
	 */
	
	@Transactional
	public Customer getCustomer(String email) {
		Customer c = entityManager.find(Customer.class, email); // Look for the Customer associated with the email
		return c;
	}

	/** Create and return a DailySchedule instance
	 * 
	 * @param dayOfWeek
	 * @param startTime
	 * @param endTime
	 * @return d
	 */
	
	@Transactional
	public DailySchedule createDailySchedule(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
		DailySchedule d = new DailySchedule(dayOfWeek, startTime, endTime); // Creation of DailySchedule instance
		entityManager.persist(d); // Persist DailySchedule instance
		return d;
	}

	/** Return the DailySchedule associated with the given id.
	 * 
	 * @param id
	 * @return d
	 */
	@Transactional
	public DailySchedule getDailySchedule(Long id) {
		DailySchedule d = entityManager.find(DailySchedule.class, id); // Look for the DailySchedule associated with the id
		return d;
	}

	/** Create and return an Employee instance
	 * 
	 * @param email
	 * @param password
	 * @param name
	 * @param salary
	 * @return e
	 */
	@Transactional
	public Employee createEmployee(String email, String password, String name, int salary) {
		Employee e = new Employee(email, password, name, salary); // Creation of Employee instance
		entityManager.persist(e); // Persist Employee instance
		return e;
	}
	
	
	/** Return the Employee associated with the given id.
	 * 
	 * @param email
	 * @return
	 */
	
	@Transactional
	public Employee getEmployee(String email) {
		Employee e = entityManager.find(Employee.class, email);  // Look for the DailySchedule associated with the email
		return e;
	}
	
	/** Create and return a Store instance
	 * 
	 * @param aDeliveryFee
	 * @param aTown
	 * @return c
	 */
	@Transactional
	public Store createStore(double aDeliveryFee, String aTown) {
		Store s = new Store(aDeliveryFee, aTown); // Creation of Store instance
		entityManager.persist(s); // Persist Order instance
		return s;
	}

	/** Return the Store associated with the given id.
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public Store getStore(Long id) {
		Store s = entityManager.find(Store.class, id); // Look for the Store associated with the id
		return s;
	}

	/** Create and return an Order instance
	 * 
	 * @param orderType
	 * @param orderStatus
	 * @param date
	 * @param time
	 * @param customer
	 * @return o
	 */
	@Transactional
	public Order createOrder(OrderType orderType, OrderStatus orderStatus, Date date, Time time, Customer customer) {
		Order o = new Order(orderType, orderStatus, date, time, customer); // Creation of Order instance
		entityManager.persist(o); // Persist Order instance
		return o;
	}

	/** Return the Order associated with the given id.
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public Order getOrder(Long id) {
		Order o = entityManager.find(Order.class, id); // Look for the Order associated with the id
		return o;
	}

	/** Create and return an OrderItem instance
	 * 
	 * @param quantity
	 * @param item
	 * @return oi
	 */ 
	@Transactional
	public OrderItem createOrderItem(int quantity, ShoppableItem item) {
		OrderItem oi = new OrderItem(); // Creation of OrderItem instance
		// Set the OrderItem instance attributes
		oi.setItem(item); 
		oi.setQuantity(quantity);
		entityManager.persist(oi); // Persist OrderIten instance
		return oi;
	}

	/** Return the OrderItem associated with the given id.
	 * 
	 * @param id
	 * @return oi
	 */
	@Transactional
	public OrderItem getOrderItem(Long id) {
		OrderItem oi = entityManager.find(OrderItem.class, id); // Look for the OrderItem associated with the id
		return oi;
	}

	/** Create and return an Owner instance
	 * 
	 * @param email
	 * @param password
	 * @param name
	 * @return o
	 */
	@Transactional
	public Owner createOwner(String email, String password, String name) {
		Owner o = new Owner(email, password, name); // Creation of Owner instance
		entityManager.persist(o); // Persist Owner instance
		return o;
	}

	/** Return the Owner associated with the given id.
	 * 
	 * @param email
	 * @return o
	 */
	@Transactional
	public Owner getOwner(String email) {
		Owner o = entityManager.find(Owner.class, email); // Look for the Owner associated with the email
		return o;
	}

	/** Create and return a Review instance
	 * 
	 * @param rating
	 * @param description
	 * @param customer
	 * @param order
	 * @return r
	 */
	@Transactional
	public Review createReview(Rating rating, String description, Customer customer, Order order) {
		Review r = new Review(rating, description, customer, order); // Creation of Review instance
		entityManager.persist(r); // Persist Review instance
		return r;
	}

	/** Return the Review associated with the given id.
	 * 
	 * @param id
	 * @return r
	 */
	@Transactional
	public Review getReview(Long id) {
		Review r = entityManager.find(Review.class, id); // Look for the Review associated with the id
		return r;
	}

	/** Create and return a ShoppableItem instance
	 * 
	 * @param name
	 * @param price
	 * @param quantityAvailable
	 * @return si
	 */
	@Transactional
	public ShoppableItem createShoppableItem(String name, double price, int quantityAvailable) {
		ShoppableItem si = new ShoppableItem(name, price, quantityAvailable); // Creation of ShoppableItem instance
		entityManager.persist(si); // Persist ShoppableItem instance
		return si;
	}

	/** Return the ShoppableItem associated with the given name
	 * 
	 * @param name
	 * @return si
	 */
	@Transactional
	public ShoppableItem ShoppableItem(String name) {
		ShoppableItem si = entityManager.find(ShoppableItem.class, name); // Look for the ShoppableItem associated with the name
		return si;
	}

	/** Create and return a UnavailableItem instance
	 * 
	 * @param name
	 * @param price
	 * @return ui
	 */
	@Transactional
	public UnavailableItem createUnavailableItem(String name, double price) {
		UnavailableItem ui = new UnavailableItem(name, price); // Creation of UnavailableItem instance
		entityManager.persist(ui); // Persist UnavailableItem instance
		return ui;
	}

	/** Return the UnavailableItem associated with the given name
	 * 
	 * @param name
	 * @return
	 */
	@Transactional
	public UnavailableItem getUnavailableItem(String name) {
		UnavailableItem ui = entityManager.find(UnavailableItem.class, name); // Look for the UnavailableItem associated with the name
		return ui;
	}

}
