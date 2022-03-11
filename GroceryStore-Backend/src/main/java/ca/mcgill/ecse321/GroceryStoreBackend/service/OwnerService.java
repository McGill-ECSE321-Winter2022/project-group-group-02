package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.OwnerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Owner;

@Service
public class OwnerService {


  @Autowired
  OwnerRepository ownerRepository;



  @Transactional
  public Owner createOwner(String email, String password, String name) {

    if (ownerRepository.findByEmail("admin@grocerystore.com") != null) {
      throw new IllegalArgumentException("Owner already exists");
    }

    if (name == null)
      throw new IllegalArgumentException("Name cannot be null");
    if (name.isBlank())
      throw new IllegalArgumentException("Name cannot be blank");
    if (!email.equals("admin@grocerystore.com"))
      throw new IllegalArgumentException(
          "Invalid email, use system email \"admin@grocerystore.com\"");


    Owner owner = new Owner();
    owner.setName(name);

    if (passwordIsValid(password)) {
      owner.setPassword(password);
    }
    owner.setEmail(email);
    ownerRepository.save(owner);
    return owner;
  }



  @Transactional
  public Owner updateOwnerPassword(String newPassword) {
    Owner oldOwner = ownerRepository.findByEmail("admin@grocerystore.com");

    if (passwordIsValid(newPassword)) {
      oldOwner.setPassword(newPassword);
    }

    oldOwner = ownerRepository.save(oldOwner);


    return oldOwner;
  }

  @Transactional
  public Owner updateOwnerName(String newName) {
    Owner oldOwner = ownerRepository.findByEmail("admin@grocerystore.com");

    if (newName == null)
      throw new IllegalArgumentException("Name cannot be null");
    if (newName.isBlank())
      throw new IllegalArgumentException("Name cannot be blank");

    oldOwner.setName(newName);

    oldOwner = ownerRepository.save(oldOwner);


    return oldOwner;
  }

  @Transactional
  public Owner getOwner() {
    Owner owner = ownerRepository.findByEmail("admin@grocerystore.com");
    return owner;
  }
  
  @Transactional
  public boolean deleteOwner() throws IllegalArgumentException {

    Owner owner = ownerRepository.findByEmail("admin@grocerystore.com");
    

      if (owner == null) {
          throw new IllegalArgumentException("Owner not found.");
      }

      ownerRepository.delete(owner);
      return true;
  }


  private boolean passwordIsValid(String password) {

    if (password == null)
      throw new IllegalArgumentException("Password cannot be null");
    if (password.isBlank())
      throw new IllegalArgumentException("Password cannot be blank");

    return true;
  }



}
