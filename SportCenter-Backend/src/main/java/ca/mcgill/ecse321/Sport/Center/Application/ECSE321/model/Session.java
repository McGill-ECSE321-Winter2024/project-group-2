package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.sql.Time;

import java.sql.Date;

// line 32 "domainModel.ump"
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
  @Basic(fetch = FetchType.EAGER)
  private int length;
  @Basic(fetch = FetchType.EAGER)
  private Time startTime;
  @Basic(fetch = FetchType.EAGER)
  private Time endTime;
  @Basic(fetch = FetchType.EAGER)
  private Date date;
  @Basic(fetch = FetchType.EAGER)
  private boolean isRepeating;
  @Basic(fetch = FetchType.EAGER)
  private int maxParticipants;

  //Session Associations
  @ManyToOne
  private ClassType classType;
  @ManyToOne
  private Instructor instructor;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Session(){}
  public Session(int aLength, Time aStartTime, Time aEndTime, Date aDate, boolean aIsRepeating, int aMaxParticipants, ClassType aClassType, Instructor aInstructor)
  {
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
}