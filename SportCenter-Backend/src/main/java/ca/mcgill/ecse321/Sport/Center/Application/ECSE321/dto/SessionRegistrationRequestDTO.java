package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;

public class SessionRegistrationRequestDTO {
    private String sessionId;
    private String customerId;

    public SessionRegistrationRequestDTO() {
    }

    public SessionRegistrationRequestDTO(String sessionId, String customerId) {
        this.sessionId = sessionId;
        this.customerId = customerId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
