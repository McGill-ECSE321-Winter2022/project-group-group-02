package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



// line 57 "model.ump"
// line 165 "model.ump"
public class OrderItem
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OrderItem Attributes
  private int quantity;

  //Autounique Attributes
  private int id;

  //OrderItem Associations
  private ShoppableItem item;
  private GroceryStoreApplication groceryStoreApplication;
  private Order order;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public OrderItem(int aQuantity, ShoppableItem aItem, GroceryStoreApplication aGroceryStoreApplication, Order aOrder)
  {
    quantity = aQuantity;
    id = nextId++;
    if (!setItem(aItem))
    {
      throw new RuntimeException("Unable to create OrderItem due to aItem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGroceryStoreApplication = setGroceryStoreApplication(aGroceryStoreApplication);
    if (!didAddGroceryStoreApplication)
    {
      throw new RuntimeException("Unable to create orderItem due to groceryStoreApplication. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddOrder = setOrder(aOrder);
    if (!didAddOrder)
    {
      throw new RuntimeException("Unable to create orderItem due to order. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public ShoppableItem getItem()
  {
    return item;
  }
  /* Code from template association_GetOne */
  public GroceryStoreApplication getGroceryStoreApplication()
  {
    return groceryStoreApplication;
  }
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setItem(ShoppableItem aNewItem)
  {
    boolean wasSet = false;
    if (aNewItem != null)
    {
      item = aNewItem;
      wasSet = true;
    }
    return wasSet;
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
      existingGroceryStoreApplication.removeOrderItem(this);
    }
    groceryStoreApplication.addOrderItem(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setOrder(Order aOrder)
  {
    boolean wasSet = false;
    if (aOrder == null)
    {
      return wasSet;
    }

    Order existingOrder = order;
    order = aOrder;
    if (existingOrder != null && !existingOrder.equals(aOrder))
    {
      existingOrder.removeOrderItem(this);
    }
    order.addOrderItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    item = null;
    GroceryStoreApplication placeholderGroceryStoreApplication = groceryStoreApplication;
    this.groceryStoreApplication = null;
    if(placeholderGroceryStoreApplication != null)
    {
      placeholderGroceryStoreApplication.removeOrderItem(this);
    }
    Order placeholderOrder = order;
    this.order = null;
    if(placeholderOrder != null)
    {
      placeholderOrder.removeOrderItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "groceryStoreApplication = "+(getGroceryStoreApplication()!=null?Integer.toHexString(System.identityHashCode(getGroceryStoreApplication())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
  }
}