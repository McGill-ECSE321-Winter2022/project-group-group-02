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
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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

	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}

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

	public void setCustomer(Customer aNewCustomer) {
		customer = aNewCustomer;
	}

	public static int minimumNumberOfOrderItems() {
		return 0;
	}

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
		while (orderItems.size() > 0) {
			OrderItem aOrderItem = orderItems.get(orderItems.size() - 1);
			aOrderItem.delete();
			orderItems.remove(aOrderItem);
		}
	}

}