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
public class UnavailableItemController {

  @Autowired
  private UnavailableItemService unavailableItemService;



  @GetMapping(value = {"/view_unavailable_item"})
  public List<UnavailableItemDto> getAllUnavailableItems() {
    return unavailableItemService.getAllUnavailableItems().stream().map(UnavailableItem -> convertToDTO(UnavailableItem))
        .collect(Collectors.toList());
  }



  @GetMapping(value = {"/view_unavailable_item/{name}"})
  public UnavailableItemDto viewUnavailableItem(@PathVariable("name") String name) {
    return convertToDTO(unavailableItemService.getUnavailableItem(name));
  }


  @PostMapping(value = {"/create_unavailable_item"})
  public ResponseEntity<?> createUnavailableItem(@RequestParam("name") String name,
      @RequestParam("price") double price) {

    UnavailableItem UnavailableItem = null;

    try {
      UnavailableItem = unavailableItemService.createItem(name, price);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(UnavailableItem), HttpStatus.CREATED);
  }

  @PostMapping(value = {"/update_price/{Name}"})
  public UnavailableItemDto updatePrice(@PathVariable("name") String name,
      @RequestParam("newPrice") double newPrice) {
    UnavailableItem UnavailableItem = unavailableItemService.updatePrice(name, newPrice);
    return convertToDTO(UnavailableItem);
  }
  
  
  public static UnavailableItemDto convertToDTO(UnavailableItem unavailableItem) {
    if (unavailableItem == null)
      throw new IllegalArgumentException("UnavailableItem not found.");
    return new UnavailableItemDto(unavailableItem.getName(), unavailableItem.getPrice());
  }



}