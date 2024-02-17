package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

// line 13 "model.ump"
// line 134 "model.ump"
@Entity
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  @Id
  @GeneratedValue
  private int personId;
  private String password;
  private String email;
  private String name;

  //Person Associations
  @OneToOne
  private Role role;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Person()
  {
  }

  public Person(int aPersonId, String aPassword, String aEmail, String aName, Role aRole)
  {
    personId = aPersonId;
    password = aPassword;
    email = aEmail;
    name = aName;
    if (!setRole(aRole))
    {
      throw new RuntimeException("Unable to create Person due to aRole. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPersonId(int aPersonId)
  {
    boolean wasSet = false;
    personId = aPersonId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getPersonId()
  {
    return personId;
  }

  public String getPassword()
  {
    return password;
  }

  public String getEmail()
  {
    return email;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public Role getRole()
  {
    return role;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setRole(Role aNewRole)
  {
    boolean wasSet = false;
    if (aNewRole != null)
    {
      role = aNewRole;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    role = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "personId" + ":" + getPersonId()+ "," +
            "password" + ":" + getPassword()+ "," +
            "email" + ":" + getEmail()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "role = "+(getRole()!=null?Integer.toHexString(System.identityHashCode(getRole())):"null");
  }
}