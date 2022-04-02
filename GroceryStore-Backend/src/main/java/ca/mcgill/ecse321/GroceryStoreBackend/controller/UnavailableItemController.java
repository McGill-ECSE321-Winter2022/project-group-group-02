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
public class UnavailableItemController {

  @Autowired
  private UnavailableItemService unavailableItemService;

  /**
   * This method returns a list containing all the unavailable items in the system.
   * 
   * @return list of all UnavailableItems all converted to Dto
   * 
   * @author Ralph Nassar
   */

  @GetMapping(value = {"/view_all_unavailable_item"})
  public List<UnavailableItemDto> getAllUnavailableItems() {
    return unavailableItemService.getAllUnavailableItems().stream().map(UnavailableItem -> convertToDTO(UnavailableItem))
        .collect(Collectors.toList());
  }

  /**
   * Returns an unavailable item. The unavailable item is found in the system with its name.
   * 
   * @param name
   * @return an unavailable item converted to Dto
   * 
   * @author Ralph Nassar
   */

  @GetMapping(value = {"/view_unavailable_item"})
  public UnavailableItemDto viewUnavailableItem(@RequestParam("name") String name) {
    return convertToDTO(unavailableItemService.getUnavailableItem(name));
  }

  /**
   *  Creates an unavailable item. An unavailable item can be found in the system with its name, and has parameter price
   * 
   * @param name
   * @param price
   * @return the created unavailable item converted to Dto
   * 
   * @author Ralph Nassar
   */

  @PostMapping(value = {"/create_unavailable_item"})
  public ResponseEntity<?> createUnavailableItem(@RequestParam("name") String name,
      @RequestParam("price") double price) {

    UnavailableItem UnavailableItem = null;

    try {
      UnavailableItem = unavailableItemService.createUnavailableItem(name, price);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(UnavailableItem), HttpStatus.CREATED);
  }
  
  /**
   * Update the price of an existing unavailable item
   * 
   * @param name
   * @param newPrice
   * @return the updated unavailable item converted to Dto
   * 
   * @author Ralph Nassar
   */

  @PutMapping(value = {"/update_unavailable_item_price"})
  public ResponseEntity<?> updatePrice(@RequestParam("name") String name,
      @RequestParam("newPrice") double newPrice) {
    UnavailableItem UnavailableItem = unavailableItemService.updatePrice(name, newPrice);
    return new ResponseEntity<>(convertToDTO(UnavailableItem), HttpStatus.CREATED);
  }
  
  /**
   * Deletes an unavailable item from the system
   * @param name
   * 
   * @author Ralph Nassar
   */
  
  @DeleteMapping(value = {"/delete_unavailable_item"})
  public void deleteUnavailableItem (@RequestParam("name") String name) {
	  try {
          unavailableItemService.deleteUnavailableItem(name);
      } catch (Exception e) {
          String error = e.getMessage();
      }
  }
  
  /**
   * Convert an unavailable item to DTO
   * @param unavailableItem
   * @return the converted unavailable item
   * 
   * @author Ralph Nassar
   */
  
  public static UnavailableItemDto convertToDTO(UnavailableItem unavailableItem) {
    if (unavailableItem == null)
      throw new IllegalArgumentException("UnavailableItem not found.");
    return new UnavailableItemDto(unavailableItem.getName(), unavailableItem.getPrice());
  }



}
