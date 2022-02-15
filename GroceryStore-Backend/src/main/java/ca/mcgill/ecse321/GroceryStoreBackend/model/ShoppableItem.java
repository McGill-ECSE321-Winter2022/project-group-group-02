package ca.mcgill.ecse321.GroceryStoreBackend.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 63 "model.ump"
// line 148 "model.ump"
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

  public ShoppableItem(String aName, double aPrice, GroceryStoreApplication aGroceryStoreApplication, int aQuantityAvailable)
  {
    super(aName, aPrice, aGroceryStoreApplication);
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