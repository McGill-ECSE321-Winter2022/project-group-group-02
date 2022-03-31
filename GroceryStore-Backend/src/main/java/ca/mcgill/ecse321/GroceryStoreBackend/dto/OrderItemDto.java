package ca.mcgill.ecse321.GroceryStoreBackend.dto;


public class OrderItemDto {

  private int quantity;
  private ShoppableItemDto item;
  private Long id;
  
  
  public OrderItemDto() {
    
  }
  
  public OrderItemDto(int quantity, ShoppableItemDto item, Long id) {
    
    this.quantity = quantity;
    this.item = item;
    this.id = id;
   
  }
  
  public int getQuantity() {
    
    return this.quantity;
  }
  
  public Long getId() {
    
    return this.id;
  }
  
  public ShoppableItemDto getShoppableItem() {
    
    return this.item;
  }
  
  
}
