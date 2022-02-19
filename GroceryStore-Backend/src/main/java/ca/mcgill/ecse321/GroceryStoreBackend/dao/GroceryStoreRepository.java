package ca.mcgill.ecse321.GroceryStoreBackend.dao;



import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
  
  @Transactional
  public Customer createCustomer(String email, String password, String name, String address) {
	  Customer c = new Customer(email, password, name, address);
	  entityManager.persist(c);
	  return c;
  }
  
  @Transactional
  public Customer getCustomer(String email) {
	  Customer c = entityManager.find(Customer.class, email);
	  return c;
  }
  
  @Transactional
  public DailySchedule createDailySchedule(DayOfWeek dayOfWeek, Time startTime, Time endTime) {
      DailySchedule d = new DailySchedule(dayOfWeek, startTime, endTime);
      entityManager.persist(d);
      return d;
  }
  
  @Transactional
  public DailySchedule getDailySchedule(Long id) {
	  DailySchedule d = entityManager.find(DailySchedule.class, id);
	  return d;
  }
  
  @Transactional
  public Employee createEmployee(String email, String password, String name, int salary) {
	  Employee e = new Employee(email, password, name, salary);
	  entityManager.persist(e);
	  return e;
  }
  
  @Transactional
  public Store getStore(Long id) {
      Store e = entityManager.find(Store.class, id);
	  return e;
  }
  
  @Transactional
  public Store createStore(double aDeliveryFee, String aTown) {
      Store e = new Store(aDeliveryFee, aTown);
      entityManager.persist(e);
      return e;
  }
  
  @Transactional
  public Employee getEmployee(String email) {
      Employee e = entityManager.find(Employee.class, email);
      return e;
  }
  
  @Transactional
  public Order createOrder(OrderType orderType, OrderStatus orderStatus, Date date, Time time, Customer customer) {
	  Order o = new Order(orderType, orderStatus, date, time, customer);
	  entityManager.persist(o);
	  return o;
  }
  
  @Transactional
  public Order getOrder(Long id) {
	  Order o = entityManager.find(Order.class, id);
	  return o;
  }
  
  @Transactional
  public OrderItem createOrderItem(int quantity, ShoppableItem item) {
	  OrderItem oi =  new OrderItem();
	  oi.setItem(item);
	  oi.setQuantity(quantity);
	  return oi;
  }
  
  @Transactional
  public OrderItem getOrderItem(Long id) {
	  OrderItem oi = entityManager.find(OrderItem.class, id);
	  return oi;
  }
  
  @Transactional
  public Owner createOwner(String email, String password, String name) {
	  Owner o = new Owner(email, password, name);
	  entityManager.persist(o);
	  return o;
  }
  
  @Transactional
  public Owner getOwner(String email) {
	  Owner o = entityManager.find(Owner.class, email);
	  return o;
  }
  
  @Transactional
  public Review createReview(Rating rating, String description, Customer customer, Order order) {
	  Review r = new Review(rating, description, customer, order);
	  entityManager.persist(r);
	  return r;
  }
  
  @Transactional
  public Review getReview(Long id) {
	  Review r = entityManager.find(Review.class, id);
	  return r;
  }
  
  
  @Transactional
  public ShoppableItem createShoppableItem(String name, double price, int quantityAvailable) {
	  ShoppableItem si = new ShoppableItem(name, price, quantityAvailable);
	  entityManager.persist(si);
	  return si;  
  }
  
  @Transactional
  public ShoppableItem ShoppableItem(String name) {
	  ShoppableItem si = entityManager.find(ShoppableItem.class, name);
	  return si;
  }
  
  @Transactional
  public UnavailableItem createUnavailableItem(String name, double price) {
	  UnavailableItem ui = new UnavailableItem(name, price);
	  entityManager.persist(ui);
	  return ui;  
  }
  
  @Transactional
  public UnavailableItem getUnavailableItem(String name) {
	  UnavailableItem ui = entityManager.find(UnavailableItem.class, name);
	  return ui;
  }
  
  
}
