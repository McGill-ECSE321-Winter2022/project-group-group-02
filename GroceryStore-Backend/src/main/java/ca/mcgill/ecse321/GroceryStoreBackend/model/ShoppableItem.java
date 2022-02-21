package ca.mcgill.ecse321.GroceryStoreBackend.model;

import javax.persistence.*;

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

  public ShoppableItem() {
    super();
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