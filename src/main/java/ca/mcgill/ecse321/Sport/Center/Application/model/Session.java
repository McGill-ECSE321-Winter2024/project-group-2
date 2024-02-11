package ca.mcgill.ecse321.Sport.Center.Application.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.sql.Time;

// line 40 "domainModel.ump"
public class Session
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Session Attributes
  private Time startTime;
  private Time endTime;
  private String dayOfWeek;
  private boolean isRepeating;

  //Session Associations
  private Class class;
  private Schedule schedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Session(Time aStartTime, Time aEndTime, String aDayOfWeek, boolean aIsRepeating, Class aClass, Schedule aSchedule)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    dayOfWeek = aDayOfWeek;
    isRepeating = aIsRepeating;
    if (!setClass(aClass))
    {
      throw new RuntimeException("Unable to create Session due to aClass. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setSchedule(aSchedule))
    {
      throw new RuntimeException("Unable to create Session due to aSchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public boolean setDayOfWeek(String aDayOfWeek)
  {
    boolean wasSet = false;
    dayOfWeek = aDayOfWeek;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsRepeating(boolean aIsRepeating)
  {
    boolean wasSet = false;
    isRepeating = aIsRepeating;
    wasSet = true;
    return wasSet;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public String getDayOfWeek()
  {
    return dayOfWeek;
  }

  public boolean getIsRepeating()
  {
    return isRepeating;
  }
  /* Code from template association_GetOne */
  public Class getClass()
  {
    return class;
  }
  /* Code from template association_GetOne */
  public Schedule getSchedule()
  {
    return schedule;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setClass(Class aNewClass)
  {
    boolean wasSet = false;
    if (aNewClass != null)
    {
      class = aNewClass;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSchedule(Schedule aNewSchedule)
  {
    boolean wasSet = false;
    if (aNewSchedule != null)
    {
      schedule = aNewSchedule;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    class = null;
    schedule = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "dayOfWeek" + ":" + getDayOfWeek()+ "," +
            "isRepeating" + ":" + getIsRepeating()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "class = "+(getClass()!=null?Integer.toHexString(System.identityHashCode(getClass())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "schedule = "+(getSchedule()!=null?Integer.toHexString(System.identityHashCode(getSchedule())):"null");
  }
}