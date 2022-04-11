package ca.mcgill.ecse321.GroceryStoreBackend.model;

import java.util.*;
import javax.persistence.*;

@Entity
public class Employee extends Person {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Employee Attributes
  private double salary;
  private String password;

  // Employee Associations
  private List<DailySchedule> dailySchedules;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Employee(String aEmail, String aPassword, String aName, double aSalary) {
    super(aEmail, aPassword, aName);
    password = "1234";
    salary = aSalary;
    dailySchedules = new ArrayList<DailySchedule>();
  }

  public Employee() {
    super();
  }
  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setSalary(double aSalary) {
    boolean wasSet = false;
    salary = aSalary;
    wasSet = true;
    return wasSet;
  }

  public double getSalary() {
    return salary;
  }

  /* Code from template association_GetMany */
  public DailySchedule getDailySchedule(int index) {
    DailySchedule aDailySchedule = dailySchedules.get(index);
    return aDailySchedule;
  }

  @ManyToMany
  public List<DailySchedule> getDailySchedules() {

    return dailySchedules;
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

  /* Code from template association_SetUnidirectionalOptionalN */
  public boolean setDailySchedules(List<DailySchedule> newDailySchedules) {
    this.dailySchedules = newDailySchedules;
    return true;
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
    super.delete();
  }

  public String toString() {
    return super.toString() + "[" +
        "salary" + ":" + getSalary() + "," +
        "password" + ":" + getPassword() + "]";
  }
}