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
public class OwnerController {

  @Autowired
  private OwnerService ownerService;



  @GetMapping(value = {"/view_owner"})
  public List<OwnerDto> getAllOwners() {
    return ownerService.getAllOwners().stream().map(owner -> convertToDTO(owner))
        .collect(Collectors.toList());
  }



  @GetMapping(value = {"/view_owner/{email}"})
  public OwnerDto viewOwner(@PathVariable("email") String email) {
    return convertToDTO(ownerService.getOwner(email));
  }


  @PostMapping(value = {"/create_owner"})
  public ResponseEntity<?> createOwner(@RequestParam("name") String name,
      @RequestParam("password") String password, @RequestParam("email") String email) {

    Owner owner = null;

    try {
      owner = ownerService.createOwner(email, password, name);
    } catch (IllegalArgumentException exception) {
      return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(convertToDTO(owner), HttpStatus.CREATED);
  }

  @PostMapping(value = {"/update_owner/{oldEmail}"})
  public OwnerDto updateOwner(@PathVariable("email") String email,
      @RequestParam("newPassword") String newPassword) {
    Owner owner = ownerService.updateOwner(email, newPassword);
    return convertToDTO(owner);
  }


  public static OwnerDto convertToDTO(Owner owner) {
    if (owner == null)
      throw new IllegalArgumentException("Owner not found.");
    return new OwnerDto(owner.getEmail(), owner.getPassword(), owner.getName());
  }



}
