package ca.mcgill.ecse321.GroceryStoreBackend.dto;

import java.sql.Date;
import java.sql.Time;

import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderStatus;
import ca.mcgill.ecse321.GroceryStoreBackend.model.Order.OrderType;

public class OrderDto {

	private OrderType orderType;
	private OrderStatus orderStatus;
	private Date date;
	private Time time;

	public OrderDto() {
		}

	public OrderDto(OrderType orderType, OrderStatus orderStatus, Date date, Time time) {
			this.orderType = orderType;
			this.orderStatus = orderStatus;
			this.date = date;
			this.time = time;
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

}
