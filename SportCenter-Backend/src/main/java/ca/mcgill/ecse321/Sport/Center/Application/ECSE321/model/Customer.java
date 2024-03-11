package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

// line 24 "domainModel.ump"


@Entity
public class Customer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Associations
  @OneToOne
  private Person person;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Customer(){}
  public Customer(Person aPerson)
  {
    if (!setPerson(aPerson))
    {
      throw new RuntimeException("Unable to create Customer due to aPerson. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Person getPerson()
  {
    return person;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPerson(Person aNewPerson)
  {
    boolean wasSet = false;
    if (aNewPerson != null)
    {
      person = aNewPerson;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    person = null;
  }

}