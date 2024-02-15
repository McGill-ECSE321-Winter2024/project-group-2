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

  public Owner(int aEmployeeId, Person aPerson, SportCenter aSportCenter) {
    super(aEmployeeId);
    if (!setPerson(aPerson)) {
      throw new RuntimeException(
          "Unable to create Owner due to aPerson. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter) {
      throw new RuntimeException(
          "Unable to create owner due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------
  /* Code from template association_GetOne */
  public Person getPerson() {
    return Person;
  }

  /* Code from template association_GetOne */
  public SportCenter getSportCenter() {
    return sportCenter;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setPerson(Person aNewPerson) {
    boolean wasSet = false;
    if (aNewPerson != null) {
      Person = aNewPerson;
      wasSet = true;
    }
    return wasSet;
  }

  /* Code from template association_SetOneToOptionalOne */
  public boolean setSportCenter(SportCenter aNewSportCenter) {
    boolean wasSet = false;
    if (aNewSportCenter == null) {
      // Unable to setSportCenter to null, as owner must always be associated to a
      // sportCenter
      return wasSet;
    }

    Owner existingOwner = aNewSportCenter.getOwner();
    if (existingOwner != null && !equals(existingOwner)) {
      // Unable to setSportCenter, the current sportCenter already has a owner, which
      // would be orphaned if it were re-assigned
      return wasSet;
    }

    SportCenter anOldSportCenter = sportCenter;
    sportCenter = aNewSportCenter;
    sportCenter.setOwner(this);

    if (anOldSportCenter != null) {
      anOldSportCenter.setOwner(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    Person = null;
    SportCenter existingSportCenter = sportCenter;
    sportCenter = null;
    if (existingSportCenter != null) {
      existingSportCenter.setOwner(null);
    }
    super.delete();
  }

}