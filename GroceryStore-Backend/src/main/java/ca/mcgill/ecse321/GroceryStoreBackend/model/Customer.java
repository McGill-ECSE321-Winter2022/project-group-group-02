package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import javax.persistence.*;

// line 24 "model.ump"
// line 96 "model.ump"
@Entity
public class Customer extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String address;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aEmail, String aPassword, String aName, String aAddress)
  {
    super(aEmail, aPassword, aName);
    address = aAddress;
  }
  
  public Customer() {
      super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public String getAddress()
  {
    return address;
  }
  

  public void delete()
  {
    super.delete();
  }
  


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "]";
  }
}