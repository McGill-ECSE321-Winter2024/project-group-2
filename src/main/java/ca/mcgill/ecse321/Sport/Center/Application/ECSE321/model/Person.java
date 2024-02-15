package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/*PLEASE DO NOT EDIT THIS CODE*/

/*This code was generated using the UMPLE 1.33.0.6934.a386b0a58 modeling language!*/

// line 13 "model.ump"
// line 77 "model.ump"

@Entity
public class Person {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // User Attributes

  @Id
  @GeneratedValue
  private int id;
  private String password;
  private String email;
  private String name;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  // Hibernate needs a default constructor, but it doesn't need to be public
  @SuppressWarnings("unused")
  protected Person() {
  }

  public Person(String aPassword, String aEmail, String aName) {
    password = aPassword;
    email = aEmail;
    name = aName;
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword) {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail) {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName) {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public void delete() {
  }

  public String toString() {
    return super.toString() + "[" +
        "id" + ":" + getId() + "," +
        "password" + ":" + getPassword() + "," +
        "email" + ":" + getEmail() + "," +
        "name" + ":" + getName() + "]";
  }
}