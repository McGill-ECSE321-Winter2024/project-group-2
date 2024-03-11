package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;
// line 43 "domainModel.ump"
import jakarta.persistence.OneToOne;

@Entity
public class Owner
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  @OneToOne
  private Person person;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Owner(){}
  public Owner(Person aPerson)
  {
    if (!setPerson(aPerson))
    {
      throw new RuntimeException("Unable to create Owner due to aPerson. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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