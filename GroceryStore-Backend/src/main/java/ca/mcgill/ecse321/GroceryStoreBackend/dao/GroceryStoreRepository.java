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


@Repository
public class GroceryStoreRepository {

  @Autowired
  EntityManager entityManager;
  
  @Transactional
  public Customer createCustomer() {
	  return null;
  }
  
  @Transactional
  public Customer getCustomer() {
    return null;
  }
  
  @Transactional
  public DailySchedule createDailySchedule() {
      return null;
  }
  
  @Transactional
  public DailySchedule getDailySchedule() {
    return null;
  }
  
  @Transactional
  public Employee createEmployee() {
      return null;
  }
  
  @Transactional
  public Employee getEmployee() {
    return null;
  }
  
  @Transactional
  public GroceryStoreApplication createGroceryStoreApplication() {
      return null;
  }
  
  @Transactional
  public GroceryStoreApplication getGroceryStoreApplication() {
    return null;
  }
  
  @Transactional
  public Order createOrder() {
      return null;
  }
  
  @Transactional
  public Order getOrder() {
    return null;
  }
  
  @Transactional
  public OrderItem createOrderItem() {
      return null;
  }
  
  @Transactional
  public OrderItem getOrderItem() {
    return null;
  }
  
  @Transactional
  public Owner createOwner() {
      return null;
  }
  
  @Transactional
  public Owner getOwner() {
    return null;
  }
  
  @Transactional
  public Review createReview() {
      return null;
  }
  
  @Transactional
  public Review getReview() {
    return null;
  }
  
  
  @Transactional
  public ShoppableItem createShoppableItem() {
      return null;
  }
  
  @Transactional
  public ShoppableItem ShoppableItem() {
    return null;
  }
  
  @Transactional
  public UnavailableItem createUnavailableItem() {
      return null;
  }
  
  @Transactional
  public UnavailableItem getUnavailableItem() {
    return null;
  }
  
  @Transactional
  public boolean cleanDatabase() {
    return false;
  }
  
}
