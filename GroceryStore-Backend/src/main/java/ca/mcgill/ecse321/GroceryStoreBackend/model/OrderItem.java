package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

// line 52 "model.ump"
// line 131 "model.ump"
@Entity
@Table(name="OrderItems")
public class OrderItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //OrderItem Attributes
  private Long id;
  private int quantity;

  //OrderItem Associations
  private ShoppableItem item;
  //private Order order;

  //------------------------
  // CONSTRUCTOR
  //------------------------

//  public OrderItem(int aQuantity, ShoppableItem aItem, Order aOrder)
//  {
//    quantity = aQuantity;
//    if (!setItem(aItem))
//    {
//      throw new RuntimeException("Unable to create OrderItem due to aItem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//    boolean didAddOrder = setOrder(aOrder);
//    if (!didAddOrder)
//    {
//      throw new RuntimeException("Unable to create orderItem due to order. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
//    }
//  }
  
  public OrderItem() {}

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(Long aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  @Id
  public Long getId()
  {
    return id;
  }

  public int getQuantity()
  {
    return quantity;
  }
  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public ShoppableItem getItem()
  {
    return item;
  }
  
  
 
//  /* Code from template association_GetOne */
//  @ManyToOne(optional=false)
//  public Order getOrder()
//  {
//    return order;
//  }
  
  
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
//  /* Code from template association_SetOneToMany */
//  public boolean setOrder(Order aOrder)
//  {
//    boolean wasSet = false;
//    if (aOrder == null)
//    {
//      return wasSet;
//    }
//
//    Order existingOrder = order;
//    order = aOrder;
//    if (existingOrder != null && !existingOrder.equals(aOrder))
//    {
//      existingOrder.removeOrderItem(this);
//    }
//    order.addOrderItem(this);
//    wasSet = true;
//    return wasSet;
//  }
  

  public void delete()
  {
    item = null;

  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null");
    
  }
}