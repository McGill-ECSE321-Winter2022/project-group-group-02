package ca.mcgill.ecse321.GroceryStoreBackend.dto;


public class OrderItemDto {

  private int quantity;
  private ShoppableItemDto item;
  
  
  public OrderItemDto() {
    
  }
  
  public OrderItemDto(int quantity, ShoppableItemDto item) {
    
    this.quantity = quantity;
    this.item = item;
   
  }
  
  public int getQuantity() {
    
    return this.quantity;
  }
  
  public ShoppableItemDto getShoppableItem() {
    
    return this.item;
  }
  
  
}
