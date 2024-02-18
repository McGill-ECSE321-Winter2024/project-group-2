package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * The SessionRegistration class represents the registration of a customer for a
 * session in the sports center.
 * It includes attributes such as id and has many-to-one associations with the
 * Session and Customer classes.
 */
@Entity
public class SessionRegistration {

  // SessionRegistration Attributes
  @Id
  @GeneratedValue
  private int id;

  // SessionRegistration Associations
  @ManyToOne
  private Session session;
  @ManyToOne
  private Customer customer;

  /**
   * Default constructor for SessionRegistration.
   */
  public SessionRegistration() {
  }

  /**
   * Parameterized constructor for SessionRegistration.
   * 
   * @param aId       The unique identifier for the session registration.
   * @param aSession  The associated session for the registration.
   * @param aCustomer The associated customer for the registration.
   * @throws RuntimeException if unable to create SessionRegistration due to
   *                          aSession or aCustomer.
   *                          See
   *                          http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html
   */
  public SessionRegistration(int aId, Session aSession, Customer aCustomer) {
    id = aId;
    if (!setSession(aSession)) {
      throw new RuntimeException(
          "Unable to create SessionRegistration due to aSession. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setCustomer(aCustomer)) {
      throw new RuntimeException(
          "Unable to create SessionRegistration due to aCustomer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  /**
   * Sets the id attribute of the session registration.
   * 
   * @param aId The new value for id.
   * @return True if the operation was successful, false otherwise.
   */
  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  /**
   * Gets the id attribute of the session registration.
   * 
   * @return The id.
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the associated session for the session registration.
   * 
   * @return The session.
   */
  /* Code from template association_GetOne */
  public Session getSession() {
    return session;
  }

  /**
   * Gets the associated customer for the session registration.
   * 
   * @return The customer.
   */
  /* Code from template association_GetOne */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * Sets the associated session for the session registration.
   * 
   * @param aNewSession The new session to be associated with the registration.
   * @return True if the operation was successful, false otherwise.
   */
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSession(Session aNewSession) {
    boolean wasSet = false;
    if (aNewSession != null) {
      session = aNewSession;
      wasSet = true;
    }
    return wasSet;
  }

  /**
   * Sets the associated customer for the session registration.
   * 
   * @param aNewCustomer The new customer to be associated with the registration.
   * @return True if the operation was successful, false otherwise.
   */
  /* Code from template association_SetUnidirectionalOne */
  public boolean setCustomer(Customer aNewCustomer) {
    boolean wasSet = false;
    if (aNewCustomer != null) {
      customer = aNewCustomer;
      wasSet = true;
    }
    return wasSet;
  }

  /**
   * Deletes the associated session and customer for the session registration.
   */
  public void delete() {
    session = null;
    customer = null;
  }

  /**
   * Generates a string representation of the SessionRegistration, including its
   * attribute id,
   * and associated session and customer.
   * 
   * @return A string representation of the SessionRegistration.
   */
  public String toString() {
    return super.toString() + "[" +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "session = "
        + (getSession() != null ? Integer.toHexString(System.identityHashCode(getSession())) : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "customer = "
        + (getCustomer() != null ? Integer.toHexString(System.identityHashCode(getCustomer())) : "null");
  }
}