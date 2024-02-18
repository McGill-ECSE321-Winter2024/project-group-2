package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;

/**
 * The Staff class represents an abstract entity that serves as a base class for
 * different staff roles
 * within the sports center: Owner and Instructor. It is part of a joined
 * inheritance strategy.
 */
@Entity
public abstract class Staff extends Role {

  /**
   * Default constructor for Staff.
   */
  public Staff() {
    super();
  }

  /**
   * Parameterized constructor for Staff.
   * 
   * @param aId The unique identifier for the staff.
   */
  public Staff(int aId) {
    super(aId);
  }

  /**
   * Deletes the Staff. This method is empty in the current implementation
   * and can be extended as needed in future versions.
   */
  public void delete() {
    super.delete();
  }

}