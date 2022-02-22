package ca.mcgill.ecse321.GroceryStoreBackend.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Stores")
public class Store {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// Store Attributes
	private double deliveryFee;
	private String town;
	private Long id;

	// Store Associations
	private List<DailySchedule> dailySchedules;

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public Store(double aDeliveryFee, String aTown) {
		deliveryFee = aDeliveryFee;
		town = aTown;
		dailySchedules = new ArrayList<DailySchedule>();
	}

	public Store() {
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public boolean setDeliveryFee(double aDeliveryFee) {
		boolean wasSet = false;
		deliveryFee = aDeliveryFee;
		wasSet = true;
		return wasSet;
	}

	public boolean setTown(String aTown) {
		boolean wasSet = false;
		town = aTown;
		wasSet = true;
		return wasSet;
	}

	public boolean setId(Long aId) {

		this.id = aId;
		return true;
	}

	public double getDeliveryFee() {
		return deliveryFee;
	}

	public String getTown() {
		return town;
	}

	@Id
	public Long getId() {
		return id;
	}

	/* Code from template association_GetMany */
	public DailySchedule getDailySchedule(int index) {
		DailySchedule aDailySchedule = dailySchedules.get(index);
		return aDailySchedule;
	}

	//@OneToMany(cascade = {CascadeType.ALL})
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<DailySchedule> getDailySchedules() {
		return this.dailySchedules;
	}

	public void setDailySchedules(List<DailySchedule> dailySchedules) {

		this.dailySchedules = dailySchedules;
	}

	public int numberOfDailySchedules() {
		int number = dailySchedules.size();
		return number;
	}

	public boolean hasDailySchedules() {
		boolean has = dailySchedules.size() > 0;
		return has;
	}

	public int indexOfDailySchedule(DailySchedule aDailySchedule) {
		int index = dailySchedules.indexOf(aDailySchedule);
		return index;
	}

	/* Code from template association_MinimumNumberOfMethod */
	public static int minimumNumberOfDailySchedules() {
		return 0;
	}

	/* Code from template association_MaximumNumberOfMethod */
	public static int maximumNumberOfDailySchedules() {
		return 7;
	}

	/* Code from template association_AddUnidirectionalOptionalN */
	public boolean addDailySchedule(DailySchedule aDailySchedule) {
		boolean wasAdded = false;
		if (dailySchedules.contains(aDailySchedule)) {
			return false;
		}
		if (numberOfDailySchedules() < maximumNumberOfDailySchedules()) {
			dailySchedules.add(aDailySchedule);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean removeDailySchedule(DailySchedule aDailySchedule) {
		boolean wasRemoved = false;
		if (dailySchedules.contains(aDailySchedule)) {
			dailySchedules.remove(aDailySchedule);
			wasRemoved = true;
		}
		return wasRemoved;
	}


	/* Code from template association_AddIndexControlFunctions */
	public boolean addDailyScheduleAt(DailySchedule aDailySchedule, int index) {
		boolean wasAdded = false;
		if (addDailySchedule(aDailySchedule)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfDailySchedules()) {
				index = numberOfDailySchedules() - 1;
			}
			dailySchedules.remove(aDailySchedule);
			dailySchedules.add(index, aDailySchedule);
			wasAdded = true;
		}
		return wasAdded;
	}

	public boolean addOrMoveDailyScheduleAt(DailySchedule aDailySchedule, int index) {
		boolean wasAdded = false;
		if (dailySchedules.contains(aDailySchedule)) {
			if (index < 0) {
				index = 0;
			}
			if (index > numberOfDailySchedules()) {
				index = numberOfDailySchedules() - 1;
			}
			dailySchedules.remove(aDailySchedule);
			dailySchedules.add(index, aDailySchedule);
			wasAdded = true;
		} else {
			wasAdded = addDailyScheduleAt(aDailySchedule, index);
		}
		return wasAdded;
	}

	public void delete() {
		dailySchedules.clear();
	}

	public String toString() {
		return super.toString() + "[" + "deliveryFee" + ":" + getDeliveryFee() + "," + "town" + ":" + getTown() + ","
				+ "id" + ":" + getId() + "]";
	}
}