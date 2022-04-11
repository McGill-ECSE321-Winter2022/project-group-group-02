package ca.mcgill.ecse321.GroceryStoreBackend.model;

import java.sql.Time;
import javax.persistence.*;

@Entity
@Table(name = "DailySchedules")
public class DailySchedule {

	// ------------------------
	// ENUMERATIONS
	// ------------------------

	public enum DayOfWeek {
		Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
	}

	// DailySchedule Attributes

	private Long id;
	private DayOfWeek dayOfWeek;
	private Time startTime;
	private Time endTime;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public DailySchedule(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime) {
		dayOfWeek = aDayOfWeek;
		startTime = aStartTime;
		endTime = aEndTime;
	}

	public DailySchedule() {
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setDayOfWeek(DayOfWeek aDayOfWeek) {
		boolean wasSet = false;
		dayOfWeek = aDayOfWeek;
		wasSet = true;
		return wasSet;
	}

	public boolean setStartTime(Time aStartTime) {
		boolean wasSet = false;
		startTime = aStartTime;
		wasSet = true;
		return wasSet;
	}

	public boolean setEndTime(Time aEndTime) {
		boolean wasSet = false;
		endTime = aEndTime;
		wasSet = true;
		return wasSet;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long getId() {
		return this.id;
	}

	public void setId(Long aId) {
		this.id = aId;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void delete() {
	}
}