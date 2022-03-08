package ca.mcgill.ecse321.GroceryStoreBackend.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule;

public class StoreDto {

	private Double deliveryFee;
	private String town;
	private Long id;
	private List<DailySchedule> dailySchedules;
	
	public StoreDto() {
		}

	public StoreDto(Double deliveryFee, String town, Long id, List<DailySchedule> dailySchedules) {
			this.deliveryFee = deliveryFee;
			this.town = town;
			this.id = id;
			this.dailySchedules = dailySchedules;
		}

	public Double getDeliveryFee() {
		return deliveryFee;
	}
	
	public String getTown() {
		return town;
	}
	
	public Long getId() {
		return id;
	}
	
	
	public List<DailySchedule> getDailySchedule() {
		return dailySchedules;
	}

    public void setDailySchedules(List<DailySchedule> dailySchedules) {
		this.dailySchedules = dailySchedules;
	}

}