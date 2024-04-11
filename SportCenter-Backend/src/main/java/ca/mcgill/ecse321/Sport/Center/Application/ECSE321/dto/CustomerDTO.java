package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

import java.util.List;

import ca.mcgill.ecse321.Sport.Center.Application.ECSE321.model.Customer;

public class CustomerDTO {
    private List<SessionRegistrationDTO> sessions;
    private int customerId;
    private int personId;

    public CustomerDTO() {
    }

    public CustomerDTO(List<SessionRegistrationDTO> sessionRegistrationDTOs) {
        this.sessions = sessionRegistrationDTOs;
    }


    public CustomerDTO(int id, List<SessionRegistrationDTO> sessionRegistrationDTOs) {
        this.sessions = sessionRegistrationDTOs;
        this.customerId = id;
    }
  
    public CustomerDTO(Customer customer){
        this.customerId = customer.getId();
        this.personId = customer.getPerson().getId();

    }

    public List<SessionRegistrationDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionRegistrationDTO> sessions) {
        this.sessions = sessions;
    }

    public int getId() {
        return customerId;
    }

    public void setId(int cid) {
        this.customerId = cid;
    }

}
