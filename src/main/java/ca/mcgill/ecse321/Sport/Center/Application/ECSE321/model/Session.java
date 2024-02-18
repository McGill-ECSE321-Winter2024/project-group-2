package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import java.sql.Time;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

/**
 * The Session class represents a session in the sports center.
 * It includes attributes such as id, length, startTime, endTime, date, isRepeating, and maxParticipants.
 * Additionally, it has many-to-one associations with the ClassType and Instructor classes.
 */
@Entity
public class Session
{

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

  
  /**
   * Default constructor for Session.
   */
  public Session(){

  }

  /**
   * Parameterized constructor for Session.
   * 
   * @param aId The unique identifier for the session.
   * @param aLength The length of the session.
   * @param aStartTime The start time of the session.
   * @param aEndTime The end time of the session.
   * @param aDate The date of the session.
   * @param aIsRepeating A boolean indicating whether the session is repeating.
   * @param aMaxParticipants The maximum number of participants for the session.
   * @param aClassType The associated class type for the session.
   * @param aInstructor The associated instructor for the session.
   * @throws RuntimeException if unable to create Session due to aClassType or aInstructor.
   * See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html
   */
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

  /**
   * Sets the id attribute of the session.
   * 
   * @param aId The new value for id.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the length attribute of the session.
   * 
   * @param aLength The new value for length.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setLength(int aLength)
  {
    boolean wasSet = false;
    length = aLength;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the startTime attribute of the session.
   * 
   * @param aStartTime The new value for startTime.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the endTime attribute of the session.
   * 
   * @param aEndTime The new value for endTime.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the date attribute of the session.
   * 
   * @param aDate The new value for date.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the isRepeating attribute of the session.
   * 
   * @param aIsRepeating The new value for isRepeating.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setIsRepeating(boolean aIsRepeating)
  {
    boolean wasSet = false;
    isRepeating = aIsRepeating;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the maxParticipants attribute of the session.
   * 
   * @param aMaxParticipants The new value for maxParticipants.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setMaxParticipants(int aMaxParticipants)
  {
    boolean wasSet = false;
    maxParticipants = aMaxParticipants;
    wasSet = true;
    return wasSet;
  }

  /**
   * Gets the id attribute of the session.
   * 
   * @return The id.
   */
  public int getId()
  {
    return id;
  }

  /**
   * Gets the length attribute of the session.
   * 
   * @return The length.
   */
  public int getLength()
  {
    return length;
  }

  /**
   * Gets the startTime attribute of the session.
   * 
   * @return The startTime.
   */
  public Time getStartTime()
  {
    return startTime;
  }

  /**
   * Gets the endTime attribute of the session.
   * 
   * @return The endTime.
   */
  public Time getEndTime()
  {
    return endTime;
  }

  /**
   * Gets the date attribute of the session.
   * 
   * @return The date.
   */
  public Date getDate()
  {
    return date;
  }
  
  /**
   * Gets the isRepeating attribute of the session.
   * 
   * @return True if the session is repeating, false otherwise.
   */
  public boolean getIsRepeating()
  {
    return isRepeating;
  }

  /**
   * Gets the maxParticipants attribute of the session.
   * 
   * @return The maxParticipants.
   */
  public int getMaxParticipants()
  {
    return maxParticipants;
  }
    
  
  /**
   * Gets the associated class type of the session.
   * 
   * @return The class type.
   */
  /* Code from template association_GetOne */
  public ClassType getClassType()
  {
    return classType;
  }

  
  /**
   * Gets the associated instructor of the session.
   * 
   * @return The instructor.
   */
  /* Code from template association_GetOne */
  public Instructor getInstructor()
  {
    return instructor;
  }

  /**
   * Sets the associated class type of the session.
   * 
   * @param aNewClassType The new class type to be associated with the session.
   * @return True if the operation was successful, false otherwise.
   */
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

  /**
   * Sets the associated instructor of the session.
   * 
   * @param aNewInstructor The new instructor to be associated with the session.
   * @return True if the operation was successful, false otherwise.
   */
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

  /**
   * Deletes the associated class type and instructor of the session.
   */
  public void delete()
  {
    classType = null;
    instructor = null;
  }

  /**
   * Generates a string representation of the Session, including its attributes,
   * start time, end time, date, and associated class type and instructor.
   * 
   * @return A string representation of the Session.
   */
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