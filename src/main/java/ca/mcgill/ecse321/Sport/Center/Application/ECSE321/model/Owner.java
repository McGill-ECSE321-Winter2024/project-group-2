package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;
/*PLEASE DO NOT EDIT THIS CODE*/

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

// line 53 "model.ump"
// line 102 "model.ump"
@Entity
public class Owner extends Staff {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Owner Associations
  @OneToOne
  private Person Person;
  @OneToOne(optional=false)
  private SportCenter sportCenter;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Owner(int aEmployeeId, Person aPerson, SportCenter aSportCenter)
  {
    super(aEmployeeId);
    if (!setPerson(aPerson))
    {
      throw new RuntimeException("Unable to create Owner due to aPerson. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aSportCenter == null || aSportCenter.getOwner() != null)
    {
      throw new RuntimeException("Unable to create Owner due to aSportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    sportCenter = aSportCenter;
  }

  public Owner(int aEmployeeId, Person aPerson)
  {
    super(aEmployeeId);
    boolean didAddPerson = setPerson(aPerson);
    if (!didAddPerson)
    {
      throw new RuntimeException("Unable to create owner due to person. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    sportCenter = new SportCenter(this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Person getPerson()
  {
    return Person;
  }
  /* Code from template association_GetOne */
  public SportCenter getSportCenter()
  {
    return sportCenter;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPerson(Person aNewPerson)
  {
    boolean wasSet = false;
    if (aNewPerson != null)
    {
      Person = aNewPerson;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    Person = null;
    SportCenter existingSportCenter = sportCenter;
    sportCenter = null;
    if (existingSportCenter != null)
    {
      existingSportCenter.delete();
    }
    super.delete();
  }

}