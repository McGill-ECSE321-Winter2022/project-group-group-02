package ca.mcgill.ecse321.GroceryStoreBackend.model;

import javax.persistence.*;

@Entity
public class Owner extends Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Attributes
  private String email;
  private String password;

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