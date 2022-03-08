package ca.mcgill.ecse321.GroceryStoreBackend.dto;

import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;

public class OrderItemDto {

  private int quantity;
  private ShoppableItem item;
  
  
  public OrderItemDto() {
    
  }
  
  public OrderItemDto(int quantity, ShoppableItem item) {
    
    this.quantity = quantity;
    this.item = item;
   
  }
  
  public int getQuantity() {
    
    return this.quantity;
  }
  
  public ShoppableItem getShoppableItem() {
    
    return this.item;
  }
  
  
}
