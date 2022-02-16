package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

// line 28 "model.ump"
// line 130 "model.ump"
@Entity
public class Customer extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Attributes
  private String address;

  //Customer Associations
  private GroceryStoreApplication groceryStoreApplication;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  
  public Customer(String aEmail, String aPassword, String aName, String aAddress, GroceryStoreApplication aGroceryStoreApplication)
  {
    super(aEmail, aPassword, aName);
    address = aAddress;
    boolean didAddGroceryStoreApplication = setGroceryStoreApplication(aGroceryStoreApplication);
    if (!didAddGroceryStoreApplication)
    {
      throw new RuntimeException("Unable to create customer due to groceryStoreApplication. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public GroceryStoreApplication getGroceryStoreApplication()
  {
    return groceryStoreApplication;
  }
  
  /* Code from template association_SetOneToMany */
  public boolean setGroceryStoreApplication(GroceryStoreApplication aGroceryStoreApplication)
  {
    boolean wasSet = false;
    if (aGroceryStoreApplication == null)
    {
      return wasSet;
    }

    GroceryStoreApplication existingGroceryStoreApplication = groceryStoreApplication;
    groceryStoreApplication = aGroceryStoreApplication;
    if (existingGroceryStoreApplication != null && !existingGroceryStoreApplication.equals(aGroceryStoreApplication))
    {
      existingGroceryStoreApplication.removeCustomer(this);
    }
    groceryStoreApplication.addCustomer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    GroceryStoreApplication placeholderGroceryStoreApplication = groceryStoreApplication;
    this.groceryStoreApplication = null;
    if(placeholderGroceryStoreApplication != null)
    {
      placeholderGroceryStoreApplication.removeCustomer(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "groceryStoreApplication = "+(getGroceryStoreApplication()!=null?Integer.toHexString(System.identityHashCode(getGroceryStoreApplication())):"null");
  }
}