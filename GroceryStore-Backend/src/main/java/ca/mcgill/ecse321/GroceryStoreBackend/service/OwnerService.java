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
    ownerRepository.save(owner);
    return owner;
  }



  @Transactional
  public Owner updateOwner(String email, String newPassword) {
    Owner oldOwner = ownerRepository.findByEmail(email);
    if (!oldOwner.getPassword().equals(newPassword) && !newPassword.equals(newPassword)) {
      if (passwordIsValid(newPassword)) {
        oldOwner.setPassword(newPassword);
      }
      ownerRepository.save(oldOwner);

    }
    return oldOwner;
  }

  @Transactional
  public Owner getOwner(String email) {
    Owner owner = ownerRepository.findByEmail(email);
    return owner;
  }
  
  @Transactional
  public List<Owner> getAllOwners(){             
      return toList(ownerRepository.findAll());
  }


  private boolean passwordIsValid(String password) {
    if (password.length() < 4)
      throw new IllegalArgumentException("Password must have at least 4 characters");
    if (password.length() > 20)
      throw new IllegalArgumentException("Password must not have more than 20 characters");

    boolean upperCaseFlag = false;
    boolean lowerCaseFlag = false;
    boolean numberFlag = false;

    for (int i = 0; i < password.length(); i++) {
      if (Character.isUpperCase(password.charAt(i)))
        upperCaseFlag = true;
      else if (Character.isLowerCase(password.charAt(i)))
        lowerCaseFlag = true;
      else if (Character.isDigit(password.charAt(i)))
        numberFlag = true;
    }

    if (upperCaseFlag == false)
      throw new IllegalArgumentException("Password must contain at least one uppercase character");
    if (lowerCaseFlag == false)
      throw new IllegalArgumentException("Password must contain at least one lowercase character");
    if (numberFlag == false)
      throw new IllegalArgumentException("Password must contain at least one numeric character");

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
