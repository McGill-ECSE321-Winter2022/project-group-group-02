package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.GroceryStoreBackend.dto.*;
import ca.mcgill.ecse321.GroceryStoreBackend.model.*;
import ca.mcgill.ecse321.GroceryStoreBackend.service.*;


@CrossOrigin(origins = "*")
@RestController
public class ShoppableItemController {

  @Autowired
  private ShoppableItemService shoppableItemService;


  /**
   * This method returns a list containing all the shoppable items in the system
   * 
   * @return list of all ShoppableItem all converted to Dto
   * 
   * @author Ralph Nassar
   */
  
  @GetMapping(value = {"/view_all_shoppable_item"})
  public List<ShoppableItemDto> getAllShoppableItems() {
    return shoppableItemService.getAllShoppableItems().stream().map(ShoppableItem -> convertToDTO(ShoppableItem))
        .collect(Collectors.toList());
  }

  /**
   * Returns a shoppable item. The shoppable item is found in the system with its name
   * 
   * @param name
   * @return a shoppable item converted to Dto
   * 
   * @author Ralph Nassar
   */

  @GetMapping(value = {"/view_shoppable_item/{name}"})
  public ShoppableItemDto viewShoppableItem(@PathVariable("name") String name) {
    return convertToDTO(shoppableItemService.getShoppableItem(name));
  }

  /**
   *  Creates a shoppable item. A shoppable item can be found in the system with its name, and has parameters price and quantity available
   * 
   * @param name
   * @param price
   * @param quantityAvailable
   * @return the created shoppable item converted to Dto
   * 
   * @author Ralph Nassar
   */
  
  @PostMapping(value = {"/create_shoppable_item"})
  public ResponseEntity<?> createShoppableItem(@RequestParam("name") String name,
      @RequestParam("price") double price, @RequestParam("quantity available") int quantityAvailable) {

    ShoppableItem shoppableItem = null;

    try {
      shoppableItem = shoppableItemService.createShoppableItem(name, price, quantityAvailable);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(shoppableItem), HttpStatus.CREATED);
  }
  
  /**
   * Update the price of an existing shoppable item
   * 
   * @param name
   * @param newPrice
   * @return the updated shoppable item converted to Dto
   * 
   * @author Ralph Nassar
   */
  
  @PutMapping(value = {"/update_shoppable_item_price/{name}"})
  public ShoppableItemDto updatePrice(@PathVariable("name") String name,
      @RequestParam("newPrice") double newPrice) {
    ShoppableItem shoppableItem = shoppableItemService.updatePrice(name, newPrice);
    return convertToDTO(shoppableItem);
  }
  
  /**
   * Updates the inventory of a shoppable item
   * @param name
   * @param newQuantityAvailable
   * @return the updated shoppable item converted to Dto
   * 
   * @author Ralph Nassar
   */
  
  @PutMapping(value = {"/update_shoppable_item_quantity_available/{name}"})
  public ShoppableItemDto updateQuantityAvailable(@PathVariable("name") String name,
      @RequestParam("newQuantityAvailable") int newQuantityAvailable) {
    ShoppableItem ShoppableItem = shoppableItemService.updateInventory(name, newQuantityAvailable);
    return convertToDTO(ShoppableItem);
  }
  
  /**
   * Deletes a shoppable item from the system
   * @param name
   * 
   * @author Ralph Nassar
   */
  
  @DeleteMapping(value = {"/delete_shoppable_item/{name}"})
  public void deleteShoppableItem (@PathVariable("name") String name) {
	  try {
          shoppableItemService.deleteShoppableItem(name);
      } catch (Exception e) {
          String error = e.getMessage();
      }
  }
  
  /**
   * Convert a shoppable item to DTO
   * @param shoppableItem
   * @return the converted shoppable item
   * 
   * @author Ralph Nassar
   */
  
  public static ShoppableItemDto convertToDTO(ShoppableItem shoppableItem) {
    if (shoppableItem == null)
      throw new IllegalArgumentException("Item not found.");
    return new ShoppableItemDto(shoppableItem.getName(), shoppableItem.getPrice(), shoppableItem.getQuantityAvailable());
  }



}
