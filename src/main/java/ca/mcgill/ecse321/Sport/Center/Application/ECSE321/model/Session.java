package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/


import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

// line 41 "model.ump"
// line 94 "model.ump"
@Entity
public class Session
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Session Attributes
  @Id
  @GeneratedValue
  private int id;
  private int length;
  private Time startTime;
  private Time endTime;
  private Date date;
  private boolean isRepeating;
  private int maxParticipants;

  //Session Associations
  @ManyToOne
  private ClassType classType;
  @ManyToOne
  private Instructor instructor;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Session(){

  }

  public Session(int aId, int aLength, Time aStartTime, Time aEndTime, Date aDate, boolean aIsRepeating, int aMaxParticipants, ClassType aClassType, Instructor aInstructor)
  {
    id = aId;
    length = aLength;
    startTime = aStartTime;
    endTime = aEndTime;
    date = aDate;
    isRepeating = aIsRepeating;
    maxParticipants = aMaxParticipants;
    if (!setClassType(aClassType))
    {
      throw new RuntimeException("Unable to create Session due to aClassType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setInstructor(aInstructor))
    {
      throw new RuntimeException("Unable to create Session due to aInstructor. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setLength(int aLength)
  {
    boolean wasSet = false;
    length = aLength;
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

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
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

  public boolean setMaxParticipants(int aMaxParticipants)
  {
    boolean wasSet = false;
    maxParticipants = aMaxParticipants;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }

  public int getLength()
  {
    return length;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public Date getDate()
  {
    return date;
  }

  public boolean getIsRepeating()
  {
    return isRepeating;
  }

  public int getMaxParticipants()
  {
    return maxParticipants;
  }
  /* Code from template association_GetOne */
  public ClassType getClassType()
  {
    return classType;
  }
  /* Code from template association_GetOne */
  public Instructor getInstructor()
  {
    return instructor;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setClassType(ClassType aNewClassType)
  {
    boolean wasSet = false;
    if (aNewClassType != null)
    {
      classType = aNewClassType;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setInstructor(Instructor aNewInstructor)
  {
    boolean wasSet = false;
    if (aNewInstructor != null)
    {
      instructor = aNewInstructor;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    classType = null;
    instructor = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "length" + ":" + getLength()+ "," +
            "isRepeating" + ":" + getIsRepeating()+ "," +
            "maxParticipants" + ":" + getMaxParticipants()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "classType = "+(getClassType()!=null?Integer.toHexString(System.identityHashCode(getClassType())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "instructor = "+(getInstructor()!=null?Integer.toHexString(System.identityHashCode(getInstructor())):"null");
  }
}