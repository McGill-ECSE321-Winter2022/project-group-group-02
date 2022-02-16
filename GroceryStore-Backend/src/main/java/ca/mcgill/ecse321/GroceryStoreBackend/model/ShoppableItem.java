package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

import javax.persistence.*;

// line 59 "model.ump"
// line 120 "model.ump"
@Entity
public class ShoppableItem extends Item
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ShoppableItem Attributes
  private int quantityAvailable;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ShoppableItem(String aName, double aPrice, int aQuantityAvailable)
  {
    super(aName, aPrice);
    quantityAvailable = aQuantityAvailable;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantityAvailable(int aQuantityAvailable)
  {
    boolean wasSet = false;
    quantityAvailable = aQuantityAvailable;
    wasSet = true;
    return wasSet;
  }

  public int getQuantityAvailable()
  {
    return quantityAvailable;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantityAvailable" + ":" + getQuantityAvailable()+ "]";
  }
}