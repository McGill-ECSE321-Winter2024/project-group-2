package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;

/**
 * The Instructor class represents a staff role in the sports center for an instructor.
 * This class extends the Staff class and inherits its properties and methods.
 */
@Entity
public class Instructor extends Staff
{

  /**
   * Default constructor for Instructor. Calls the default constructor of the
   * superclass (Staff) to initialize the base properties.
   */
  public Instructor()
  {
    super();
  }

  /**
   * Parameterized constructor for Instructor.
   * 
   * @param aId The unique identifier for the instructor role.
   */
  public Instructor(int aId)
  {
    super(aId);
  }

  /**
   * Deletes the Instructor. Calls the delete method of the superclass (Staff)
   * to perform any cleanup or additional actions needed when deleting an instructor.
   */
  public void delete()
  {
    super.delete();
  }

}