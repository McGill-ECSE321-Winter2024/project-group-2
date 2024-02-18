package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

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