package ca.mcgill.ecse321.GroceryStoreBackend.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;

public class OrderDto {

	private OrderType orderType;
	private OrderStatus orderStatus;
	private Date date;
	private Time time;
	private CustomerDto customer;
	private List<OrderItemDto> orderItems;
	private Long id;
	
	public OrderDto() {
		}

	public OrderDto(OrderType orderType, OrderStatus orderStatus, Date date, Time time, CustomerDto customer, List<OrderItemDto> orderItems, Long orderID) {
			this.orderType = orderType;
			this.orderStatus = orderStatus;
			this.date = date;
			this.time = time;
			this.customer = customer;
			this.orderItems = orderItems;
			this.id = orderID;
		}

	public OrderType getOrderType() {
		return orderType;
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	
	public Double getSubtotal() {
	  
	  Double subtotal = (double) 0;
	  
	  for(OrderItemDto orderItem : orderItems) {
	    
	    subtotal += orderItem.getQuantity() * orderItem.getShoppableItem().getPrice();
	  }
	  
	  return subtotal;
	}
	
	public Date getDate() {
		return date;
	}
	
	public Long getId() {
	  return id;
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
