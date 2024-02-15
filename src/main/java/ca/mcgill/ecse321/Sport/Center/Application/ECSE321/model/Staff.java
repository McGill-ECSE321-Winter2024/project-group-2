package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

// line 27 "model.ump"
// line 116 "model.ump"
public abstract class Staff extends Role {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Staff Attributes
  private int employeeId;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Staff(int aEmployeeId) {
    super();
    employeeId = aEmployeeId;
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setEmployeeId(int aEmployeeId) {
    boolean wasSet = false;
    employeeId = aEmployeeId;
    wasSet = true;
    return wasSet;
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public void delete() {
    super.delete();
  }

  public String toString() {
    return super.toString() + "[" +
        "employeeId" + ":" + getEmployeeId() + "]";
  }
}