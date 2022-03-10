package ca.mcgill.ecse321.GroceryStoreBackend.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.GroceryStoreBackend.dao.OwnerRepository;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Owner;

@Service
public class OwnerService {


  @Autowired
  OwnerRepository ownerRepository;



  @Transactional
  public Owner createOwner(String email, String password, String name) {

    if (name == "")
      throw new IllegalArgumentException("Name cannot be blank");
    if (!email.equals("admin@grocerystore.com"))
      throw new IllegalArgumentException("Invalid email,please try again.");



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
  public Owner updateOwner(String email, String newPassword) {
    Owner oldOwner = ownerRepository.findByEmail(email);

    if (passwordIsValid(newPassword)) {
      oldOwner.setPassword(newPassword);
    }
    
    oldOwner = ownerRepository.save(oldOwner);


    return oldOwner;
  }

  @Transactional
  public Owner getOwner(String email) {
    Owner owner = ownerRepository.findByEmail(email);
    return owner;
  }

  @Transactional
  public List<Owner> getAllOwners() {
    return toList(ownerRepository.findAll());
  }


  private boolean passwordIsValid(String password) {

    if (password.isBlank())
      throw new IllegalArgumentException("Password cannot be blank");

    return true;
  }



  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }

}
