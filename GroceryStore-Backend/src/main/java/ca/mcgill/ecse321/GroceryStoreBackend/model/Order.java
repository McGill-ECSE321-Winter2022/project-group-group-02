package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Date;
import java.sql.Time;
import java.util.*;

// line 72 "model.ump"
// line 158 "model.ump"
public class Order
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum OrderType { Delivery, PickUp }
  public enum OrderStatus { Confirmed, Preparing, Cancelled, Ready, Delivering, Fulfilled }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private OrderType order;
  private OrderStatus orderStatus;
  private Date date;
  private Time time;

  //Autounique Attributes
  private int id;

  //Order Associations
  private Customer customer;
  private List<OrderItem> orderItems;
  private GroceryStoreApplication groceryStoreApplication;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(OrderType aOrder, OrderStatus aOrderStatus, Date aDate, Time aTime, Customer aCustomer, GroceryStoreApplication aGroceryStoreApplication)
  {
    order = aOrder;
    orderStatus = aOrderStatus;
    date = aDate;
    time = aTime;
    id = nextId++;
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Order due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orderItems = new ArrayList<OrderItem>();
    boolean didAddGroceryStoreApplication = setGroceryStoreApplication(aGroceryStoreApplication);
    if (!didAddGroceryStoreApplication)
    {
      throw new RuntimeException("Unable to create order due to groceryStoreApplication. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOrder(OrderType aOrder)
  {
    boolean wasSet = false;
    order = aOrder;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderStatus(OrderStatus aOrderStatus)
  {
    boolean wasSet = false;
    orderStatus = aOrderStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

  public OrderType getOrder()
  {
    return order;
  }

  public OrderStatus getOrderStatus()
  {
    return orderStatus;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getTime()
  {
    return time;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetMany */
  public OrderItem getOrderItem(int index)
  {
    OrderItem aOrderItem = orderItems.get(index);
    return aOrderItem;
  }

  public List<OrderItem> getOrderItems()
  {
    List<OrderItem> newOrderItems = Collections.unmodifiableList(orderItems);
    return newOrderItems;
  }

  public int numberOfOrderItems()
  {
    int number = orderItems.size();
    return number;
  }

  public boolean hasOrderItems()
  {
    boolean has = orderItems.size() > 0;
    return has;
  }

  public int indexOfOrderItem(OrderItem aOrderItem)
  {
    int index = orderItems.indexOf(aOrderItem);
    return index;
  }
  /* Code from template association_GetOne */
  public GroceryStoreApplication getGroceryStoreApplication()
  {
    return groceryStoreApplication;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer != null)
    {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, ShoppableItem aItem, GroceryStoreApplication aGroceryStoreApplication)
  {
    return new OrderItem(aQuantity, aItem, aGroceryStoreApplication, this);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    Order existingOrder = aOrderItem.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aOrderItem.setOrder(this);
    }
    else
    {
      orderItems.add(aOrderItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderItem(OrderItem aOrderItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrderItem, as it must always have a order
    if (!this.equals(aOrderItem.getOrder()))
    {
      orderItems.remove(aOrderItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderItemAt(OrderItem aOrderItem, int index)
  {  
    boolean wasAdded = false;
    if(addOrderItem(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderItemAt(OrderItem aOrderItem, int index)
  {
    boolean wasAdded = false;
    if(orderItems.contains(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderItemAt(aOrderItem, index);
    }
    return wasAdded;
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
      existingGroceryStoreApplication.removeOrder(this);
    }
    groceryStoreApplication.addOrder(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    customer = null;
    while (orderItems.size() > 0)
    {
      OrderItem aOrderItem = orderItems.get(orderItems.size() - 1);
      aOrderItem.delete();
      orderItems.remove(aOrderItem);
    }
    
    GroceryStoreApplication placeholderGroceryStoreApplication = groceryStoreApplication;
    this.groceryStoreApplication = null;
    if(placeholderGroceryStoreApplication != null)
    {
      placeholderGroceryStoreApplication.removeOrder(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "order" + "=" + (getOrder() != null ? !getOrder().equals(this)  ? getOrder().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "orderStatus" + "=" + (getOrderStatus() != null ? !getOrderStatus().equals(this)  ? getOrderStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "groceryStoreApplication = "+(getGroceryStoreApplication()!=null?Integer.toHexString(System.identityHashCode(getGroceryStoreApplication())):"null");
  }
}