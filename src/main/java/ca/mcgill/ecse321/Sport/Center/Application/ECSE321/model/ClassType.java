package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// line 2 "model.ump"
// line 118 "model.ump"
@Entity
public class ClassType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ClassType Attributes
  @Id
  private String classType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ClassType()
  {}
  
  public ClassType(String aClassType)
  {
    classType = aClassType;
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

  public String getClassType()
  {
    return classType;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "classType" + ":" + getClassType()+ "]";
  }
}