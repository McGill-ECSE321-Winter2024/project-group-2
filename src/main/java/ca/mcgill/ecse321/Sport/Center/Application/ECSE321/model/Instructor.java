package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;
import jakarta.persistence.Entity;
/*PLEASE DO NOT EDIT THIS CODE*/
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

// line 21 "model.ump"
@Entity
public class Instructor extends Staff {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Instructor Associations
  @OneToOne
  private Person Person;
  @ManyToOne
  private SportCenter sportCenter;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Instructor(int aEmployeeId, Person aPerson, SportCenter aSportCenter) {
    super(aEmployeeId);
    if (!setPerson(aPerson)) {
      throw new RuntimeException(
          "Unable to create Instructor due to aPerson. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter) {
      throw new RuntimeException(
          "Unable to create instructor due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  /* Code from template association_SetOneToMany */
  public boolean setSportCenter(SportCenter aSportCenter) {
    boolean wasSet = false;
    if (aSportCenter == null) {
      return wasSet;
    }

    SportCenter existingSportCenter = sportCenter;
    sportCenter = aSportCenter;
    if (existingSportCenter != null && !existingSportCenter.equals(aSportCenter)) {
      existingSportCenter.removeInstructor(this);
    }
    sportCenter.addInstructor(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    Person = null;
    SportCenter placeholderSportCenter = sportCenter;
    this.sportCenter = null;
    if (placeholderSportCenter != null) {
      placeholderSportCenter.removeInstructor(this);
    }
    super.delete();
  }

}