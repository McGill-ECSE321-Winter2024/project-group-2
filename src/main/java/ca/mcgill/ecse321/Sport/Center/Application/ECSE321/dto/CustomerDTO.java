package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

import java.util.List;

public class CustomerDTO {
    private List<SessionRegistrationDTO> sessions;

    public CustomerDTO() {
    }
    public CustomerDTO(List<SessionRegistrationDTO> sessionRegistrationDTOs) {
        this.sessions = sessionRegistrationDTOs;
    }

    public List<SessionRegistrationDTO> getSessions() {
        return sessions;
    }
    public void setSessions(List<SessionRegistrationDTO> sessions) {
        this.sessions = sessions;
    }
}
