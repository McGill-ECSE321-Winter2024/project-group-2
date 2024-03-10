package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * The ClassType class represents a type of class in the sports center.
 * Each ClassType has a unique identifier (classType) and can be associated
 * with various classes offered by the sports center.
 */
@Entity
public class ClassType
{

  // ClassType Attributes
  @Id
  private String classType;

  /**
   * Default constructor for ClassType.
   */
  public ClassType()
  {}

  /**
   * Parameterized constructor for ClassType.
   * 
   * @param aClassType The unique identifier for the class type.
   */
  public ClassType(String aClassType)
  {
    classType = aClassType;
  }

  /**
   * Sets the value of the class type.
   * 
   * @param aClassType The new value for the class type.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setClassType(String aClassType)
  {
    boolean wasSet = false;
    classType = aClassType;
    wasSet = true;
    return wasSet;
  }

  /**
   * Gets the current value of the class type.
   * 
   * @return The class type.
   */
  public String getClassType()
  {
    return classType;
  }

  /**
   * Deletes the ClassType. This method is empty in the current implementation
   * and can be extended as needed in future versions.
   */
  public void delete()
  {}

  /**
   * Generates a string representation of the ClassType, including its class type.
   * 
   * @return A string representation of the ClassType.
   */
  public String toString()
  {
    return super.toString() + "["+
            "classType" + ":" + getClassType()+ "]";
  }
}