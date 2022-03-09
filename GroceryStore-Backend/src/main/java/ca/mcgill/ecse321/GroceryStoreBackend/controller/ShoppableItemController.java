package ca.mcgill.ecse321.GroceryStoreBackend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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



  @GetMapping(value = {"/view_shoppable_item"})
  public List<ShoppableItemDto> getAllShoppableItems() {
    return shoppableItemService.getAllShoppableItems().stream().map(ShoppableItem -> convertToDTO(ShoppableItem))
        .collect(Collectors.toList());
  }



  @GetMapping(value = {"/view_shoppable_item/{name}"})
  public ShoppableItemDto viewShoppableItem(@PathVariable("name") String name) {
    return convertToDTO(shoppableItemService.getShoppableItem(name));
  }


  @PostMapping(value = {"/create_ShoppableItem"})
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

  @PostMapping(value = {"/update_price/{Name}"})
  public ShoppableItemDto updatePrice(@PathVariable("name") String name,
      @RequestParam("newPrice") double newPrice) {
    ShoppableItem shoppableItem = shoppableItemService.updatePrice(name, newPrice);
    return convertToDTO(shoppableItem);
  }

  @PostMapping(value = {"/update_quantity_available/{Name}"})
  public ShoppableItemDto updateQuantityAvailable(@PathVariable("name") String name,
      @RequestParam("newPrice") int newQuantityAvailable) {
    ShoppableItem ShoppableItem = shoppableItemService.updateQuantityAvailable(name, newQuantityAvailable);
    return convertToDTO(ShoppableItem);
  }
  
  
  public static ShoppableItemDto convertToDTO(ShoppableItem shoppableItem) {
    if (shoppableItem == null)
      throw new IllegalArgumentException("Item not found.");
    return new ShoppableItemDto(shoppableItem.getName(), shoppableItem.getPrice(), shoppableItem.getQuantityAvailable());
  }



}
