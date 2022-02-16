package ca.mcgill.ecse321.GroceryStoreBackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 49 "model.ump"
// line 143 "model.ump"
@Entity
public abstract class Item
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Attributes
  private String name;
  private double price;

  //Autounique Attributes
  private int id;

  //Item Associations
  private GroceryStoreApplication groceryStoreApplication;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Item(String aName, double aPrice, GroceryStoreApplication aGroceryStoreApplication)
  {
    name = aName;
    price = aPrice;
    id = nextId++;
    boolean didAddGroceryStoreApplication = setGroceryStoreApplication(aGroceryStoreApplication);
    if (!didAddGroceryStoreApplication)
    {
      throw new RuntimeException("Unable to create item due to groceryStoreApplication. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public double getPrice()
  {
    return price;
  }

  @Id
  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
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
      existingGroceryStoreApplication.removeItem(this);
    }
    groceryStoreApplication.addItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    GroceryStoreApplication placeholderGroceryStoreApplication = groceryStoreApplication;
    this.groceryStoreApplication = null;
    if(placeholderGroceryStoreApplication != null)
    {
      placeholderGroceryStoreApplication.removeItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "groceryStoreApplication = "+(getGroceryStoreApplication()!=null?Integer.toHexString(System.identityHashCode(getGroceryStoreApplication())):"null");
  }
}