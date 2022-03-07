package ca.mcgill.ecse321.GroceryStoreBackend.dto;

import java.sql.Time;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Review.Rating;

public class ReviewDto {

  
  private Rating rating;
  private String description;

  private CustomerDto customer;
  private OrderDto order;

  public ReviewDto() {
    
  }

  public ReviewDto(Rating aRating, String aDescription, CustomerDto aCustomer, OrderDto aOrder) {
      this.rating = aRating;
      this.description = aDescription;
      this.customer = aCustomer;
      this.order = aOrder;
  }

  public Rating getRating(){

    return rating;
  }
  
  public String getDescription() {
    return description;
  }
  
  public CustomerDto getCustomer() {
    return customer;
  }
  
  public OrderDto getOrder() {
    return order;
  }
  
  public void setOrder(OrderDto order) {
     this.order = order;
  }
  
  public void setCustomer(CustomerDto customer) {
    this.customer = customer;
 }
  
}
