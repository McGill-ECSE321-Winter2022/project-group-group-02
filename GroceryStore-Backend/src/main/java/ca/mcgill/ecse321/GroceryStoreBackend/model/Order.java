package ca.mcgill.ecse321.GroceryStoreBackend.model;

import java.sql.Date;
import javax.persistence.*;
import java.sql.Time;
import java.util.*;

@Entity
@Table(name = "All_Orders")
public class Order {

	// ------------------------
	// ENUMERATIONS
	// ------------------------

	public enum OrderType {
		Delivery, PickUp
	}

	public enum OrderStatus {
		Confirmed, Preparing, Cancelled, Ready, Delivering, Fulfilled
	}

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Order Attributes

	private Long id;
	private OrderType orderType;
	private OrderStatus orderStatus;
	private Date date;
	private Time time;

	// Order Associations
	private Customer customer;
	private List<OrderItem> orderItems;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Order(OrderType aOrderType, OrderStatus aOrderStatus, Date aDate, Time aTime, Customer aCustomer) {
		orderType = aOrderType;
		orderStatus = aOrderStatus;
		date = aDate;
		time = aTime;
		setCustomer(aCustomer);
		orderItems = new ArrayList<OrderItem>();
	}

	public Order() {

		orderItems = new ArrayList<OrderItem>();
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setId(Long aId) {
		boolean wasSet = false;
		id = aId;
		wasSet = true;
		return wasSet;
	}

	public boolean setOrderType(OrderType aOrderType) {
		boolean wasSet = false;
		orderType = aOrderType;
		wasSet = true;
		return wasSet;
	}

	public boolean setOrderStatus(OrderStatus aOrderStatus) {
		boolean wasSet = false;
		orderStatus = aOrderStatus;
		wasSet = true;
		return wasSet;
	}

	public boolean setDate(Date aDate) {
		boolean wasSet = false;
		date = aDate;
		wasSet = true;
		return wasSet;
	}

	public boolean setTime(Time aTime) {
		boolean wasSet = false;
		time = aTime;
		wasSet = true;
		return wasSet;
	}

	@Id
	public Long getId() {
		return id;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public Date getDate() {
		return date;
	}

	public Time getTime() {
		return time;
	}

	/* Code from template association_GetOne */
	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}
	/* Code from template association_GetMany */

	public OrderItem getOrderItem(int index) {
		OrderItem aOrderItem = orderItems.get(index);
		return aOrderItem;
	}

	public boolean setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
		return true;
	}

	@OneToMany(fetch = FetchType.EAGER)
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public int numberOfOrderItems() {
		int number = orderItems.size();
		return number;
	}

	public boolean hasOrderItems() {
		boolean has = orderItems.size() > 0;
		return has;
	}

	public int indexOfOrderItem(OrderItem aOrderItem) {
		int index = orderItems.indexOf(aOrderItem);
		return index;
	}

	/* Code from template association_SetUnidirectionalOne */
	public void setCustomer(Customer aNewCustomer) {
		customer = aNewCustomer;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfOrderItems() {
		return 0;
	}

	/* Code from template association_AddManyToOne */
	public OrderItem addOrderItem(int aQuantity, ShoppableItem aItem) {

		OrderItem orderItem = new OrderItem();
		orderItem.setItem(aItem);
		orderItem.setQuantity(aQuantity);

		this.orderItems.add(orderItem);

		return orderItem;
	}

	public boolean addOrderItem(OrderItem aOrderItem) {

		this.orderItems.add(aOrderItem);

		return true;
	}

	public boolean removeOrderItem(OrderItem aOrderItem) {
		boolean wasRemoved = false;
		// Unable to remove aOrderItem, as it must always have a order
		if (this.getOrderItems().contains(aOrderItem)) {
			orderItems.remove(aOrderItem);
			wasRemoved = true;
		}
		return wasRemoved;
	}

	/* Code from template association_AddIndexControlFunctions */
	public boolean addOrderItemAt(OrderItem aOrderItem, int index) {
		boolean wasAdded = false;
		if (addOrderItem(aOrderItem)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfOrderItems()) {
				index = numberOfOrderItems() - 1;
			}
			orderItems.remove(aOrderItem);
			orderItems.add(index, aOrderItem);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveOrderItemAt(OrderItem aOrderItem, int index) {
		boolean wasAdded = false;
		if (orderItems.contains(aOrderItem)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfOrderItems()) {
				index = numberOfOrderItems() - 1;
			}
			orderItems.remove(aOrderItem);
			orderItems.add(index, aOrderItem);
			wasAdded = true;
		} else {
			wasAdded = addOrderItemAt(aOrderItem, index);
		}
		return wasAdded;
	}

	public void delete() {
		customer = null;
	    while (orderItems.size() > 0)
	    {
	      OrderItem aOrderItem = orderItems.get(orderItems.size() - 1);
	      aOrderItem.delete();
	      orderItems.remove(aOrderItem);
	    }
	}

	public String toString() {
		return super.toString() + "[" + "id" + ":" + getId() + "]"
				+ System.getProperties().getProperty("line.separator") + "  " + "orderType" + "="
				+ (getOrderType() != null
						? !getOrderType().equals(this) ? getOrderType().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "orderStatus" + "="
				+ (getOrderStatus() != null
						? !getOrderStatus().equals(this) ? getOrderStatus().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "date" + "="
				+ (getDate() != null ? !getDate().equals(this) ? getDate().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "time" + "="
				+ (getTime() != null ? !getTime().equals(this) ? getTime().toString().replaceAll("  ", "    ") : "this"
						: "null")
				+ System.getProperties().getProperty("line.separator") + "  " + "customer = "
				+ (getCustomer() != null ? Integer.toHexString(System.identityHashCode(getCustomer())) : "null");
	}
}