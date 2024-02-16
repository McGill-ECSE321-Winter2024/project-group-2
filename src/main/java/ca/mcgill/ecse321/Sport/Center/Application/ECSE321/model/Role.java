package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

// line 8 "model.ump"
// line 70 "model.ump"

@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Role
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Role Attributes
  @Id
  @GeneratedValue
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Role(int aId)
  {
    id = aId;
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

  public int getId()
  {
    return id;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]";
  }
}