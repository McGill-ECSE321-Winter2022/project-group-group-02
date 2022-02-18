package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;


import javax.persistence.*;

// line 11 "model.ump"
// line 161 "model.ump"
@Entity
@Inheritance
@DiscriminatorColumn(name="personColumn")
public abstract class Person
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Person> personsByEmail = new HashMap<String, Person>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String email;
  private String password;
  private String name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aEmail, String aPassword, String aName)
  {
    password = aPassword;
    name = aName;
    if (!setEmail(aEmail))
    {
      throw new RuntimeException("Cannot create due to duplicate email. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }
  
  public Person() {
    super();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    String anOldEmail = getEmail();
    if (anOldEmail != null && anOldEmail.equals(aEmail)) {
      return true;
    }
    if (hasWithEmail(aEmail)) {
      return wasSet;
    }
    email = aEmail;
    wasSet = true;
    if (anOldEmail != null) {
      personsByEmail.remove(anOldEmail);
    }
    personsByEmail.put(aEmail, this);
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  @Id
  public String getEmail()
  {
    return email;
  }
  
  /* Code from template attribute_GetUnique */
  public static Person getWithEmail(String aEmail)
  {
    return personsByEmail.get(aEmail);
  }
  
  /* Code from template attribute_HasUnique */
  public static boolean hasWithEmail(String aEmail)
  {
    return getWithEmail(aEmail) != null;
  }

  public String getPassword()
  {
    return password;
  }

  public String getName()
  {
    return name;
  }

  public void delete()
  {
    personsByEmail.remove(getEmail());
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "name" + ":" + getName()+ "]";
  }
}