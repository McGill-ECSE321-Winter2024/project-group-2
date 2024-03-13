/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/
package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;



// line 1 "domainModel.ump"

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ClassType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ClassType Attributes
  @Id
  private String classType;
  private boolean isApproved;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public ClassType(){}

  public ClassType(String aClassType, boolean aIsApproved)
  {
    classType = aClassType;
    isApproved = aIsApproved;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setClassType(String aClassType)
  {
    boolean wasSet = false;
    classType = aClassType;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsApproved(boolean aIsApproved)
  {
    boolean wasSet = false;
    isApproved = aIsApproved;
    wasSet = true;
    return wasSet;
  }

  public String getClassType()
  {
    return classType;
  }

  public boolean getIsApproved()
  {
    return isApproved;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "classType" + ":" + getClassType()+ "," +
            "isApproved" + ":" + getIsApproved()+ "]";
  }
}