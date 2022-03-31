package ca.mcgill.ecse321.GroceryStoreBackend.dto;

import java.sql.Time;

import ca.mcgill.ecse321.GroceryStoreBackend.model.DailySchedule.DayOfWeek;

public class DailyScheduleDto {

	private DayOfWeek dayOfWeek;
	private Time startTime;
	private Time endTime;
	private Long id;

	public DailyScheduleDto() {
	}

	public DailyScheduleDto(DayOfWeek dayOfWeek, Time startTime, Time endTime, Long id) {
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.id = id;
	}

	public Time getEndTime() {
		return endTime;
	}

	public Time getStartTime() {
		return startTime;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	
	public Long getId() {
	  return id;
	}

}
