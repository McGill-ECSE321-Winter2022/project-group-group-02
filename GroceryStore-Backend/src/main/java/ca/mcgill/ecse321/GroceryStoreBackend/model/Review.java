package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

// line 79 "model.ump"
// line 137 "model.ump"
@Entity
public class Review
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Rating { VeryPoor, Poor, Okay, Good, VeryGood }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes

  private Long id;
  private Rating rating;
  private String description;

  //Review Associations
  private Customer customer;
  private Order order;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(Long aId, Rating aRating, String aDescription, Customer aCustomer, Order aOrder)
  {
    id = aId;
    rating = aRating;
    description = aDescription;
    if (!setCustomer(aCustomer))
    {
      throw new RuntimeException("Unable to create Review due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setOrder(aOrder))
    {
      throw new RuntimeException("Unable to create Review due to aOrder. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

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

  public boolean setRating(Rating aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  @Id
  @GeneratedValue(generator = "increment")
  @GenericGenerator(name = "increment", strategy = "increment")
  public Long getId()
  {
    return id;
  }

  public Rating getRating()
  {
    return rating;
  }

  public String getDescription()
  {
    return description;
  }
  
  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public Customer getCustomer()
  {
    return customer;
  }
  
  /* Code from template association_GetOne */
  @ManyToOne(optional=false)
  public Order getOrder()
  {
    return order;
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
  /* Code from template association_SetUnidirectionalOne */
  public boolean setOrder(Order aNewOrder)
  {
    boolean wasSet = false;
    if (aNewOrder != null)
    {
      order = aNewOrder;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    customer = null;
    order = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "rating" + "=" + (getRating() != null ? !getRating().equals(this)  ? getRating().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
  }
}