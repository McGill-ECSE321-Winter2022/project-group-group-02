package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;

// line 22 "model.ump"
// line 125 "model.ump"
public class Owner extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Attributes
  private String email;
  private String password;

  //Owner Associations
  private GroceryStoreApplication groceryStoreApplication;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(String aEmail, String aPassword, String aName, GroceryStoreApplication aGroceryStoreApplication)
  {
    super(aEmail, aPassword, aName);
    email = "admin@grocerystore.com";
    password = "1234";
    boolean didAddGroceryStoreApplication = setGroceryStoreApplication(aGroceryStoreApplication);
    if (!didAddGroceryStoreApplication)
    {
      throw new RuntimeException("Unable to create owner due to groceryStoreApplication. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public GroceryStoreApplication getGroceryStoreApplication()
  {
    return groceryStoreApplication;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setGroceryStoreApplication(GroceryStoreApplication aNewGroceryStoreApplication)
  {
    boolean wasSet = false;
    if (aNewGroceryStoreApplication == null)
    {
      //Unable to setGroceryStoreApplication to null, as owner must always be associated to a groceryStoreApplication
      return wasSet;
    }
    
    Owner existingOwner = aNewGroceryStoreApplication.getOwner();
    if (existingOwner != null && !equals(existingOwner))
    {
      //Unable to setGroceryStoreApplication, the current groceryStoreApplication already has a owner, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    GroceryStoreApplication anOldGroceryStoreApplication = groceryStoreApplication;
    groceryStoreApplication = aNewGroceryStoreApplication;
    groceryStoreApplication.setOwner(this);

    if (anOldGroceryStoreApplication != null)
    {
      anOldGroceryStoreApplication.setOwner(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    GroceryStoreApplication existingGroceryStoreApplication = groceryStoreApplication;
    groceryStoreApplication = null;
    if (existingGroceryStoreApplication != null)
    {
      existingGroceryStoreApplication.setOwner(null);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "groceryStoreApplication = "+(getGroceryStoreApplication()!=null?Integer.toHexString(System.identityHashCode(getGroceryStoreApplication())):"null");
  }
}