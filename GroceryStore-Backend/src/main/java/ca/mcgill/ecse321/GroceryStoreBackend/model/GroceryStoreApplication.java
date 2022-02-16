package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.sql.Time;

/**
 * The start of a very beautiful class diagram
 */
// line 4 "model.ump"
// line 100 "model.ump"
// line 120 "model.ump"
// line 182 "model.ump"

@Entity
public class GroceryStoreApplication
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GroceryStoreApplication Attributes
  private double deliveryFee;

  //GroceryStoreApplication Associations
  private Owner owner;
  private List<Customer> customers;
  private List<Employee> employees;
  private List<Item> items;
  private List<Order> orders;
  private List<OrderItem> orderItems;
  private List<DailySchedule> dailySchedules;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GroceryStoreApplication(double aDeliveryFee)
  {
    deliveryFee = aDeliveryFee;
    customers = new ArrayList<Customer>();
    employees = new ArrayList<Employee>();
    items = new ArrayList<Item>();
    orders = new ArrayList<Order>();
    orderItems = new ArrayList<OrderItem>();
    dailySchedules = new ArrayList<DailySchedule>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDeliveryFee(double aDeliveryFee)
  {
    boolean wasSet = false;
    deliveryFee = aDeliveryFee;
    wasSet = true;
    return wasSet;
  }

  public double getDeliveryFee()
  {
    return deliveryFee;
  }
  /* Code from template association_GetOne */
 
  public Owner getOwner()
  {
    return owner;
  }

  public boolean hasOwner()
  {
    boolean has = owner != null;
    return has;
  }
  /* Code from template association_GetMany */
  @OneToMany(cascade={CascadeType.ALL})
  public Customer getCustomer(int index)
  {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers()
  {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public int numberOfCustomers()
  {
    int number = customers.size();
    return number;
  }

  public boolean hasCustomers()
  {
    boolean has = customers.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer)
  {
    int index = customers.indexOf(aCustomer);
    return index;
  }
  /* Code from template association_GetMany */
  @OneToMany(cascade={CascadeType.ALL})
  public Employee getEmployee(int index)
  {
    Employee aEmployee = employees.get(index);
    return aEmployee;
  }

  public List<Employee> getEmployees()
  {
    List<Employee> newEmployees = Collections.unmodifiableList(employees);
    return newEmployees;
  }

  public int numberOfEmployees()
  {
    int number = employees.size();
    return number;
  }

  public boolean hasEmployees()
  {
    boolean has = employees.size() > 0;
    return has;
  }

  public int indexOfEmployee(Employee aEmployee)
  {
    int index = employees.indexOf(aEmployee);
    return index;
  }
  /* Code from template association_GetMany */
  @OneToMany(cascade={CascadeType.ALL})
  public Item getItem(int index)
  {
    Item aItem = items.get(index);
    return aItem;
  }

  public List<Item> getItems()
  {
    List<Item> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(Item aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_GetMany */
  @OneToMany(cascade={CascadeType.ALL})
  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
  }
  /* Code from template association_GetMany */
  @OneToMany(cascade={CascadeType.ALL})
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
  /* Code from template association_GetMany */
  @OneToMany(cascade={CascadeType.ALL})
  public DailySchedule getDailySchedule(int index)
  {
    DailySchedule aDailySchedule = dailySchedules.get(index);
    return aDailySchedule;
  }

  public List<DailySchedule> getDailySchedules()
  {
    List<DailySchedule> newDailySchedules = Collections.unmodifiableList(dailySchedules);
    return newDailySchedules;
  }

  public int numberOfDailySchedules()
  {
    int number = dailySchedules.size();
    return number;
  }

  public boolean hasDailySchedules()
  {
    boolean has = dailySchedules.size() > 0;
    return has;
  }

  public int indexOfDailySchedule(DailySchedule aDailySchedule)
  {
    int index = dailySchedules.indexOf(aDailySchedule);
    return index;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setOwner(Owner aNewOwner)
  {
    boolean wasSet = false;
    if (owner != null && !owner.equals(aNewOwner) && equals(owner.getGroceryStoreApplication()))
    {
      //Unable to setOwner, as existing owner would become an orphan
      return wasSet;
    }

    owner = aNewOwner;
    GroceryStoreApplication anOldGroceryStoreApplication = aNewOwner != null ? aNewOwner.getGroceryStoreApplication() : null;

    if (!this.equals(anOldGroceryStoreApplication))
    {
      if (anOldGroceryStoreApplication != null)
      {
        anOldGroceryStoreApplication.owner = null;
      }
      if (owner != null)
      {
        owner.setGroceryStoreApplication(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Customer addCustomer(String aEmail, String aPassword, String aName, String aAddress)
  {
    return new Customer(aEmail, aPassword, aName, aAddress, this);
  }

  public boolean addCustomer(Customer aCustomer)
  {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) { return false; }
    GroceryStoreApplication existingGroceryStoreApplication = aCustomer.getGroceryStoreApplication();
    boolean isNewGroceryStoreApplication = existingGroceryStoreApplication != null && !this.equals(existingGroceryStoreApplication);
    if (isNewGroceryStoreApplication)
    {
      aCustomer.setGroceryStoreApplication(this);
    }
    else
    {
      customers.add(aCustomer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer)
  {
    boolean wasRemoved = false;
    //Unable to remove aCustomer, as it must always have a groceryStoreApplication
    if (!this.equals(aCustomer.getGroceryStoreApplication()))
    {
      customers.remove(aCustomer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerAt(Customer aCustomer, int index)
  {  
    boolean wasAdded = false;
    if(addCustomer(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index)
  {
    boolean wasAdded = false;
    if(customers.contains(aCustomer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCustomers()) { index = numberOfCustomers() - 1; }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Employee addEmployee(String aEmail, String aPassword, String aName, double aSalary)
  {
    return new Employee(aEmail, aPassword, aName, aSalary, this);
  }

  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    GroceryStoreApplication existingGroceryStoreApplication = aEmployee.getGroceryStoreApplication();
    boolean isNewGroceryStoreApplication = existingGroceryStoreApplication != null && !this.equals(existingGroceryStoreApplication);
    if (isNewGroceryStoreApplication)
    {
      aEmployee.setGroceryStoreApplication(this);
    }
    else
    {
      employees.add(aEmployee);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEmployee(Employee aEmployee)
  {
    boolean wasRemoved = false;
    //Unable to remove aEmployee, as it must always have a groceryStoreApplication
    if (!this.equals(aEmployee.getGroceryStoreApplication()))
    {
      employees.remove(aEmployee);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEmployeeAt(Employee aEmployee, int index)
  {  
    boolean wasAdded = false;
    if(addEmployee(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEmployeeAt(Employee aEmployee, int index)
  {
    boolean wasAdded = false;
    if(employees.contains(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEmployeeAt(aEmployee, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    GroceryStoreApplication existingGroceryStoreApplication = aItem.getGroceryStoreApplication();
    boolean isNewGroceryStoreApplication = existingGroceryStoreApplication != null && !this.equals(existingGroceryStoreApplication);
    if (isNewGroceryStoreApplication)
    {
      aItem.setGroceryStoreApplication(this);
    }
    else
    {
      items.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a groceryStoreApplication
    if (!this.equals(aItem.getGroceryStoreApplication()))
    {
      items.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Item aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Item aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(Order.OrderType aOrder, Order.OrderStatus aOrderStatus, Date aDate, Time aTime, Customer aCustomer)
  {
    return new Order(aOrder, aOrderStatus, aDate, aTime, aCustomer, this);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    GroceryStoreApplication existingGroceryStoreApplication = aOrder.getGroceryStoreApplication();
    boolean isNewGroceryStoreApplication = existingGroceryStoreApplication != null && !this.equals(existingGroceryStoreApplication);
    if (isNewGroceryStoreApplication)
    {
      aOrder.setGroceryStoreApplication(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a groceryStoreApplication
    if (!this.equals(aOrder.getGroceryStoreApplication()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, ShoppableItem aItem, Order aOrder)
  {
    return new OrderItem(aQuantity, aItem, this, aOrder);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    GroceryStoreApplication existingGroceryStoreApplication = aOrderItem.getGroceryStoreApplication();
    boolean isNewGroceryStoreApplication = existingGroceryStoreApplication != null && !this.equals(existingGroceryStoreApplication);
    if (isNewGroceryStoreApplication)
    {
      aOrderItem.setGroceryStoreApplication(this);
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
    //Unable to remove aOrderItem, as it must always have a groceryStoreApplication
    if (!this.equals(aOrderItem.getGroceryStoreApplication()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDailySchedules()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDailySchedules()
  {
    return 7;
  }
  /* Code from template association_AddOptionalNToOptionalOne */
  public boolean addDailySchedule(DailySchedule aDailySchedule)
  {
    boolean wasAdded = false;
    if (dailySchedules.contains(aDailySchedule)) { return false; }
    if (numberOfDailySchedules() >= maximumNumberOfDailySchedules())
    {
      return wasAdded;
    }

    GroceryStoreApplication existingApplication = aDailySchedule.getApplication();
    if (existingApplication == null)
    {
      aDailySchedule.setApplication(this);
    }
    else if (!this.equals(existingApplication))
    {
      existingApplication.removeDailySchedule(aDailySchedule);
      addDailySchedule(aDailySchedule);
    }
    else
    {
      dailySchedules.add(aDailySchedule);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDailySchedule(DailySchedule aDailySchedule)
  {
    boolean wasRemoved = false;
    if (dailySchedules.contains(aDailySchedule))
    {
      dailySchedules.remove(aDailySchedule);
      aDailySchedule.setApplication(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDailyScheduleAt(DailySchedule aDailySchedule, int index)
  {  
    boolean wasAdded = false;
    if(addDailySchedule(aDailySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDailySchedules()) { index = numberOfDailySchedules() - 1; }
      dailySchedules.remove(aDailySchedule);
      dailySchedules.add(index, aDailySchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDailyScheduleAt(DailySchedule aDailySchedule, int index)
  {
    boolean wasAdded = false;
    if(dailySchedules.contains(aDailySchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDailySchedules()) { index = numberOfDailySchedules() - 1; }
      dailySchedules.remove(aDailySchedule);
      dailySchedules.add(index, aDailySchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDailyScheduleAt(aDailySchedule, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Owner existingOwner = owner;
    owner = null;
    if (existingOwner != null)
    {
      existingOwner.delete();
      existingOwner.setGroceryStoreApplication(null);
    }
    while (customers.size() > 0)
    {
      Customer aCustomer = customers.get(customers.size() - 1);
      aCustomer.delete();
      customers.remove(aCustomer);
    }
    
    while (employees.size() > 0)
    {
      Employee aEmployee = employees.get(employees.size() - 1);
      aEmployee.delete();
      employees.remove(aEmployee);
    }
    
    while (items.size() > 0)
    {
      Item aItem = items.get(items.size() - 1);
      aItem.delete();
      items.remove(aItem);
    }
    
    while (orders.size() > 0)
    {
      Order aOrder = orders.get(orders.size() - 1);
      aOrder.delete();
      orders.remove(aOrder);
    }
    
    while (orderItems.size() > 0)
    {
      OrderItem aOrderItem = orderItems.get(orderItems.size() - 1);
      aOrderItem.delete();
      orderItems.remove(aOrderItem);
    }
    
    while (dailySchedules.size() > 0)
    {
      DailySchedule aDailySchedule = dailySchedules.get(dailySchedules.size() - 1);
      aDailySchedule.delete();
      dailySchedules.remove(aDailySchedule);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "deliveryFee" + ":" + getDeliveryFee()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null");
  }
}