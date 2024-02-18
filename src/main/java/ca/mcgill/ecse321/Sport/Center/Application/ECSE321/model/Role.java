package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

/**
 * The Role class represents an abstract entity that serves as a base class for various roles
 * within the sports center. It is part of a joined inheritance strategy.
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Role
{
  //Role Attributes
  @Id
  @GeneratedValue
  private int id;

  /**
   * Default constructor for Role.
   */
  public Role()
  {}
  
  /**
   * Parameterized constructor for Role.
   * 
   * @param aId The unique identifier for the role.
   */
  public Role(int aId)
  {
    id = aId;
  }

  /**
   * Sets the id attribute of the role.
   * 
   * @param aId The new value for id.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  /**
   * Gets the id attribute of the role.
   * 
   * @return The id.
   */
  public int getId()
  {
    return id;
  }

  /**
   * Deletes the Role. This method is empty in the current implementation
   * and can be extended as needed in future versions.
   */
  public void delete()
  {}

  /**
   * Generates a string representation of the Role, including its id.
   * 
   * @return A string representation of the Role.
   */
  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]";
  }
}