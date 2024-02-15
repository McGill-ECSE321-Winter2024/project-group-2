package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

// line 32 "model.ump"
// line 91 "model.ump"

@Entity
public class Customer extends Role {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Customer Associations
  private Person user;
  private SportCenter sportCenter;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Customer(Person aUser, SportCenter aSportCenter) {
    super();
    if (!setUser(aUser)) {
      throw new RuntimeException(
          "Unable to create Customer due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddSportCenter = setSportCenter(aSportCenter);
    if (!didAddSportCenter) {
      throw new RuntimeException(
          "Unable to create customer due to sportCenter. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------
  /* Code from template association_GetOne */
  public Person getUser() {
    return user;
  }

  /* Code from template association_GetOne */
  public SportCenter getSportCenter() {
    return sportCenter;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setUser(Person aNewUser) {
    boolean wasSet = false;
    if (aNewUser != null) {
      user = aNewUser;
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
      existingSportCenter.removeCustomer(this);
    }
    sportCenter.addCustomer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    user = null;
    SportCenter placeholderSportCenter = sportCenter;
    this.sportCenter = null;
    if (placeholderSportCenter != null) {
      placeholderSportCenter.removeCustomer(this);
    }
    super.delete();
  }

}