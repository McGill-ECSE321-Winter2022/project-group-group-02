package ca.mcgill.ecse321.GroceryStoreBackend.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse321.GroceryStoreBackend.model.Customer;
import ca.mcgill.ecse321.GroceryStoreBackend.model.OrderItem;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;

public class OrderDto {

	private OrderType orderType;
	private OrderStatus orderStatus;
	private Date date;
	private Time time;
	private CustomerDto customer;
	private List<OrderItemDto> orderItems;
	
	public OrderDto() {
		}

	public OrderDto(OrderType orderType, OrderStatus orderStatus, Date date, Time time, CustomerDto customer, List<OrderItemDto> orderItems) {
			this.orderType = orderType;
			this.orderStatus = orderStatus;
			this.date = date;
			this.time = time;
			this.customer = customer;
			this.orderItems = orderItems;
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
	
	public CustomerDto getCustomer() {
		return customer;
	}
	
	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}
	
	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}
}
