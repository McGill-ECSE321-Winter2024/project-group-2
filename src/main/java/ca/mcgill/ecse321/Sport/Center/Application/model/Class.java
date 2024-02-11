package ca.mcgill.ecse321.Sport.Center.Application.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 1 "domainModel.ump"
public class Class
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Class Attributes
  private String classType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Class(String aClassType)
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