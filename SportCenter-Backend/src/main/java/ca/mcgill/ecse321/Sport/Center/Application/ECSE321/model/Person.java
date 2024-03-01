package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

/**
 * The Person class represents an individual associated with the sports center.
 * It contains attributes such as personId, password, email, and name.
 * Additionally, it has a one-to-one association with the Role class.
 */
@Entity
public class Person
{

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

  /**
   * Default constructor for Person.
   */
  public Person()
  {
  }

  /**
   * Parameterized constructor for Person.
   * 
   * @param aPersonId The unique identifier for the person.
   * @param aPassword The password associated with the person.
   * @param aEmail The email address of the person.
   * @param aName The name of the person.
   * @param aRole The associated role for the person.
   * @throws RuntimeException if unable to create Person due to aRole.
   * See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html
   */
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

  /**
   * Sets the personId attribute of the person.
   * 
   * @param aPersonId The new value for personId.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setPersonId(int aPersonId)
  {
    boolean wasSet = false;
    personId = aPersonId;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the password attribute of the person.
   * 
   * @param aPassword The new value for password.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the email attribute of the person.
   * 
   * @param aEmail The new value for email.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  /**
   * Sets the name attribute of the person.
   * 
   * @param aName The new value for name.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  
  /**
   * Gets the personId attribute of the person.
   * 
   * @return The personId.
   */
  public int getPersonId()
  {
    return personId;
  }

  /**
   * Gets the password attribute of the person.
   * 
   * @return The password.
   */
  public String getPassword()
  {
    return password;
  }

  /**
   * Gets the email attribute of the person.
   * 
   * @return The email.
   */
  public String getEmail()
  {
    return email;
  }

  /**
   * Gets the name attribute of the person.
   * 
   * @return The name.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Gets the associated role of the person.
   * 
   * @return The role.
   */
  /* Code from template association_GetOne */
  public Role getRole()
  {
    return role;
  }

  /**
   * Sets the associated role of the person.
   * 
   * @param aNewRole The new role to be associated with the person.
   * @return True if the operation was successful, false otherwise.
   */
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

  
  /**
   * Deletes the associated role of the person.
   */
  public void delete()
  {
    role = null;
  }

  /**
   * Generates a string representation of the Person, including its attributes
   * and the associated role.
   * 
   * @return A string representation of the Person.
   */
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