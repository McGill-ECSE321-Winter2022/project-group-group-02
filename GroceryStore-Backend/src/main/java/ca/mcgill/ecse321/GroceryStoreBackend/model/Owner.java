package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import javax.persistence.*;
// line 18 "model.ump"
// line 91 "model.ump"

@Entity
public class Owner extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Attributes

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aEmail, String aPassword, String aName)
  {
    super(aEmail, aPassword, aName);
    email = "admin@grocerystore.com";
    password = "1234";
  }

  public Owner() {
    super();
  }
  //------------------------
  // INTERFACE
  //------------------------


  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}