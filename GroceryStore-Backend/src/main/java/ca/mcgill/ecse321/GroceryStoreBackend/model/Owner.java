package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import javax.persistence.*;
// line 18 "model.ump"
// line 91 "model.ump"

@Entity
@Table(name = "Owner")
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

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }


  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  @Id
  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

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