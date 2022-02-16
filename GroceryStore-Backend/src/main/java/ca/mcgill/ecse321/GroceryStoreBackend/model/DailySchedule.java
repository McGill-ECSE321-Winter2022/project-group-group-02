package ca.mcgill.ecse321.GroceryStoreBackend.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import java.sql.Time;
import javax.persistence.Entity;
import javax.persistence.Id;

// line 33 "model.ump"
// line 177 "model.ump"
@Entity
public class DailySchedule
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DailySchedule Attributes
  private DayOfWeek dayOfWeek;
  private Time startTime;
  private Time endTime;

  //Autounique Attributes
  private int id;

  //DailySchedule Associations
  private GroceryStoreApplication application;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DailySchedule(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime)
  {
    dayOfWeek = aDayOfWeek;
    startTime = aStartTime;
    endTime = aEndTime;
    id = nextId++;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDayOfWeek(DayOfWeek aDayOfWeek)
  {
    boolean wasSet = false;
    dayOfWeek = aDayOfWeek;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public DayOfWeek getDayOfWeek()
  {
    return dayOfWeek;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  @Id
  public int getId()
  {
    return id;
  }
  /* Code from template association_GetOne */
  public GroceryStoreApplication getApplication()
  {
    return application;
  }

  public boolean hasApplication()
  {
    boolean has = application != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOptionalN */
  public boolean setApplication(GroceryStoreApplication aApplication)
  {
    boolean wasSet = false;
    if (aApplication != null && aApplication.numberOfDailySchedules() >= GroceryStoreApplication.maximumNumberOfDailySchedules())
    {
      return wasSet;
    }

    GroceryStoreApplication existingApplication = application;
    application = aApplication;
    if (existingApplication != null && !existingApplication.equals(aApplication))
    {
      existingApplication.removeDailySchedule(this);
    }
    if (aApplication != null)
    {
      aApplication.addDailySchedule(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (application != null)
    {
      GroceryStoreApplication placeholderApplication = application;
      this.application = null;
      placeholderApplication.removeDailySchedule(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayOfWeek" + "=" + (getDayOfWeek() != null ? !getDayOfWeek().equals(this)  ? getDayOfWeek().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "application = "+(getApplication()!=null?Integer.toHexString(System.identityHashCode(getApplication())):"null");
  }
}