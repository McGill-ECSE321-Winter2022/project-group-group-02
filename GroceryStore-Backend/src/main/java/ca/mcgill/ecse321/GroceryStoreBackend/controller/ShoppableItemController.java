package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import ca.mcgill.ecse321.GroceryStoreBackend.dto.ShoppableItemDto;
import ca.mcgill.ecse321.GroceryStoreBackend.model.ShoppableItem;

public class ShoppableItemController {

  
  
  
  
  
  
  public static ShoppableItemDto convertToDTO(ShoppableItem shoppableItem) {
    if (shoppableItem == null)
      throw new IllegalArgumentException("Item not found.");
    
    
    
    return new ShoppableItemDto(shoppableItem.getName(), shoppableItem.getPrice(), shoppableItem.getQuantityAvailable());
  }
}
