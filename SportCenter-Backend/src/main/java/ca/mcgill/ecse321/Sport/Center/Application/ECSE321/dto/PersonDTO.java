package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Person;
import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Role;

public class PersonDTO {
    private int personId;
    private String password;
    private String email;
    private String name;
    private String role;

    public PersonDTO() {
    }
    public PersonDTO(int personId, String password, String email, String name, Role role ) {
        this.personId = personId;
        this.password = password;
        this.email = email;
        this.name = name;
        this.role = role.toString();
    }

    public PersonDTO(Person model) {
        this.personId = model.getPersonId();
        this.password = model.getPassword();
        this.email = model.getEmail();
        this.name = model.getName();
        this.role = model.getRole().toString();
    }

    public int getPersonId() {
        return personId;
    }
    public void setPersonId(int personId) {
        this.personId = personId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
