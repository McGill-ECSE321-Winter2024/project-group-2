package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 61 "model.ump"
// line 127 "model.ump"
public class SportCenter {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // SportCenter Associations
  private Owner owner;
  private List<Session> sessions;
  private List<Instructor> instructors;
  private List<Customer> customers;
  private List<SessionRegistration> sessionRegistrations;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public SportCenter() {
    sessions = new ArrayList<Session>();
    instructors = new ArrayList<Instructor>();
    customers = new ArrayList<Customer>();
    sessionRegistrations = new ArrayList<SessionRegistration>();
  }

  // ------------------------
  // INTERFACE
  // ------------------------
  /* Code from template association_GetOne */
  public Owner getOwner() {
    return owner;
  }

  public boolean hasOwner() {
    boolean has = owner != null;
    return has;
  }

  /* Code from template association_GetMany */
  public Session getSession(int index) {
    Session aSession = sessions.get(index);
    return aSession;
  }

  public List<Session> getSessions() {
    List<Session> newSessions = Collections.unmodifiableList(sessions);
    return newSessions;
  }

  public int numberOfSessions() {
    int number = sessions.size();
    return number;
  }

  public boolean hasSessions() {
    boolean has = sessions.size() > 0;
    return has;
  }

  public int indexOfSession(Session aSession) {
    int index = sessions.indexOf(aSession);
    return index;
  }

  /* Code from template association_GetMany */
  public Instructor getInstructor(int index) {
    Instructor aInstructor = instructors.get(index);
    return aInstructor;
  }

  public List<Instructor> getInstructors() {
    List<Instructor> newInstructors = Collections.unmodifiableList(instructors);
    return newInstructors;
  }

  public int numberOfInstructors() {
    int number = instructors.size();
    return number;
  }

  public boolean hasInstructors() {
    boolean has = instructors.size() > 0;
    return has;
  }

  public int indexOfInstructor(Instructor aInstructor) {
    int index = instructors.indexOf(aInstructor);
    return index;
  }

  /* Code from template association_GetMany */
  public Customer getCustomer(int index) {
    Customer aCustomer = customers.get(index);
    return aCustomer;
  }

  public List<Customer> getCustomers() {
    List<Customer> newCustomers = Collections.unmodifiableList(customers);
    return newCustomers;
  }

  public int numberOfCustomers() {
    int number = customers.size();
    return number;
  }

  public boolean hasCustomers() {
    boolean has = customers.size() > 0;
    return has;
  }

  public int indexOfCustomer(Customer aCustomer) {
    int index = customers.indexOf(aCustomer);
    return index;
  }

  /* Code from template association_GetMany */
  public SessionRegistration getSessionRegistration(int index) {
    SessionRegistration aSessionRegistration = sessionRegistrations.get(index);
    return aSessionRegistration;
  }

  public List<SessionRegistration> getSessionRegistrations() {
    List<SessionRegistration> newSessionRegistrations = Collections.unmodifiableList(sessionRegistrations);
    return newSessionRegistrations;
  }

  public int numberOfSessionRegistrations() {
    int number = sessionRegistrations.size();
    return number;
  }

  public boolean hasSessionRegistrations() {
    boolean has = sessionRegistrations.size() > 0;
    return has;
  }

  public int indexOfSessionRegistration(SessionRegistration aSessionRegistration) {
    int index = sessionRegistrations.indexOf(aSessionRegistration);
    return index;
  }

  /* Code from template association_SetOptionalOneToOne */
  public boolean setOwner(Owner aNewOwner) {
    boolean wasSet = false;
    if (owner != null && !owner.equals(aNewOwner) && equals(owner.getSportCenter())) {
      // Unable to setOwner, as existing owner would become an orphan
      return wasSet;
    }

    owner = aNewOwner;
    SportCenter anOldSportCenter = aNewOwner != null ? aNewOwner.getSportCenter() : null;

    if (!this.equals(anOldSportCenter)) {
      if (anOldSportCenter != null) {
        anOldSportCenter.owner = null;
      }
      if (owner != null) {
        owner.setSportCenter(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSessions() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Session addSession(int aId, int aLength, Time aStartTime, Time aEndTime, Date aDate, boolean aIsRepeating,
      int aMaxParticipants, ClassType aClassType, Instructor aInstructor) {
    return new Session(aId, aLength, aStartTime, aEndTime, aDate, aIsRepeating, aMaxParticipants, aClassType,
        aInstructor, this);
  }

  public boolean addSession(Session aSession) {
    boolean wasAdded = false;
    if (sessions.contains(aSession)) {
      return false;
    }
    SportCenter existingSportCenter = aSession.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter) {
      aSession.setSportCenter(this);
    } else {
      sessions.add(aSession);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSession(Session aSession) {
    boolean wasRemoved = false;
    // Unable to remove aSession, as it must always have a sportCenter
    if (!this.equals(aSession.getSportCenter())) {
      sessions.remove(aSession);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addSessionAt(Session aSession, int index) {
    boolean wasAdded = false;
    if (addSession(aSession)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfSessions()) {
        index = numberOfSessions() - 1;
      }
      sessions.remove(aSession);
      sessions.add(index, aSession);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSessionAt(Session aSession, int index) {
    boolean wasAdded = false;
    if (sessions.contains(aSession)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfSessions()) {
        index = numberOfSessions() - 1;
      }
      sessions.remove(aSession);
      sessions.add(index, aSession);
      wasAdded = true;
    } else {
      wasAdded = addSessionAt(aSession, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfInstructors() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Instructor addInstructor(int aEmployeeId, Person aUser) {
    return new Instructor(aEmployeeId, aUser, this);
  }

  public boolean addInstructor(Instructor aInstructor) {
    boolean wasAdded = false;
    if (instructors.contains(aInstructor)) {
      return false;
    }
    SportCenter existingSportCenter = aInstructor.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter) {
      aInstructor.setSportCenter(this);
    } else {
      instructors.add(aInstructor);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInstructor(Instructor aInstructor) {
    boolean wasRemoved = false;
    // Unable to remove aInstructor, as it must always have a sportCenter
    if (!this.equals(aInstructor.getSportCenter())) {
      instructors.remove(aInstructor);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addInstructorAt(Instructor aInstructor, int index) {
    boolean wasAdded = false;
    if (addInstructor(aInstructor)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfInstructors()) {
        index = numberOfInstructors() - 1;
      }
      instructors.remove(aInstructor);
      instructors.add(index, aInstructor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInstructorAt(Instructor aInstructor, int index) {
    boolean wasAdded = false;
    if (instructors.contains(aInstructor)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfInstructors()) {
        index = numberOfInstructors() - 1;
      }
      instructors.remove(aInstructor);
      instructors.add(index, aInstructor);
      wasAdded = true;
    } else {
      wasAdded = addInstructorAt(aInstructor, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCustomers() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Customer addCustomer(Person aUser) {
    return new Customer(aUser, this);
  }

  public boolean addCustomer(Customer aCustomer) {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) {
      return false;
    }
    SportCenter existingSportCenter = aCustomer.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter) {
      aCustomer.setSportCenter(this);
    } else {
      customers.add(aCustomer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCustomer(Customer aCustomer) {
    boolean wasRemoved = false;
    // Unable to remove aCustomer, as it must always have a sportCenter
    if (!this.equals(aCustomer.getSportCenter())) {
      customers.remove(aCustomer);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addCustomerAt(Customer aCustomer, int index) {
    boolean wasAdded = false;
    if (addCustomer(aCustomer)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfCustomers()) {
        index = numberOfCustomers() - 1;
      }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCustomerAt(Customer aCustomer, int index) {
    boolean wasAdded = false;
    if (customers.contains(aCustomer)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfCustomers()) {
        index = numberOfCustomers() - 1;
      }
      customers.remove(aCustomer);
      customers.add(index, aCustomer);
      wasAdded = true;
    } else {
      wasAdded = addCustomerAt(aCustomer, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSessionRegistrations() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public SessionRegistration addSessionRegistration(int aId, Session aSession, Customer aCustomer) {
    return new SessionRegistration(aId, aSession, aCustomer, this);
  }

  public boolean addSessionRegistration(SessionRegistration aSessionRegistration) {
    boolean wasAdded = false;
    if (sessionRegistrations.contains(aSessionRegistration)) {
      return false;
    }
    SportCenter existingSportCenter = aSessionRegistration.getSportCenter();
    boolean isNewSportCenter = existingSportCenter != null && !this.equals(existingSportCenter);
    if (isNewSportCenter) {
      aSessionRegistration.setSportCenter(this);
    } else {
      sessionRegistrations.add(aSessionRegistration);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSessionRegistration(SessionRegistration aSessionRegistration) {
    boolean wasRemoved = false;
    // Unable to remove aSessionRegistration, as it must always have a sportCenter
    if (!this.equals(aSessionRegistration.getSportCenter())) {
      sessionRegistrations.remove(aSessionRegistration);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addSessionRegistrationAt(SessionRegistration aSessionRegistration, int index) {
    boolean wasAdded = false;
    if (addSessionRegistration(aSessionRegistration)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfSessionRegistrations()) {
        index = numberOfSessionRegistrations() - 1;
      }
      sessionRegistrations.remove(aSessionRegistration);
      sessionRegistrations.add(index, aSessionRegistration);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSessionRegistrationAt(SessionRegistration aSessionRegistration, int index) {
    boolean wasAdded = false;
    if (sessionRegistrations.contains(aSessionRegistration)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfSessionRegistrations()) {
        index = numberOfSessionRegistrations() - 1;
      }
      sessionRegistrations.remove(aSessionRegistration);
      sessionRegistrations.add(index, aSessionRegistration);
      wasAdded = true;
    } else {
      wasAdded = addSessionRegistrationAt(aSessionRegistration, index);
    }
    return wasAdded;
  }

  public void delete() {
    Owner existingOwner = owner;
    owner = null;
    if (existingOwner != null) {
      existingOwner.delete();
      existingOwner.setSportCenter(null);
    }
    while (sessions.size() > 0) {
      Session aSession = sessions.get(sessions.size() - 1);
      aSession.delete();
      sessions.remove(aSession);
    }

    while (instructors.size() > 0) {
      Instructor aInstructor = instructors.get(instructors.size() - 1);
      aInstructor.delete();
      instructors.remove(aInstructor);
    }

    while (customers.size() > 0) {
      Customer aCustomer = customers.get(customers.size() - 1);
      aCustomer.delete();
      customers.remove(aCustomer);
    }

    while (sessionRegistrations.size() > 0) {
      SessionRegistration aSessionRegistration = sessionRegistrations.get(sessionRegistrations.size() - 1);
      aSessionRegistration.delete();
      sessionRegistrations.remove(aSessionRegistration);
    }

  }

}