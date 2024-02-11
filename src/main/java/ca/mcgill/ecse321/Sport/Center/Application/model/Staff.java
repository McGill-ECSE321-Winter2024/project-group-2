package ca.mcgill.ecse321.Sport.Center.Application.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/



// line 27 "domainModel.ump"
public abstract class Staff extends Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Staff Attributes
  private String employeeId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Staff(int aId, String aPassword, String aEmail, String aName, User aUser, String aEmployeeId)
  {
    super(aId, aPassword, aEmail, aName, aUser);
    employeeId = aEmployeeId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmployeeId(String aEmployeeId)
  {
    boolean wasSet = false;
    employeeId = aEmployeeId;
    wasSet = true;
    return wasSet;
  }

  public String getEmployeeId()
  {
    return employeeId;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "employeeId" + ":" + getEmployeeId()+ "]";
  }
}