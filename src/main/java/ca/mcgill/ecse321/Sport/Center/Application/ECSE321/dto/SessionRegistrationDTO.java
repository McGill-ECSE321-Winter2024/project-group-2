package ca.mcgill.ecse321.Sport.Center.Application.ECSE321.dto;


public class SessionRegistrationDTO {
    private int id;
    private CustomerDTO customer;
    private SessionDTO session;


    public SessionRegistrationDTO() {
    }
    public SessionRegistrationDTO(int id, CustomerDTO customer, SessionDTO session) {
        this.id = id;
        this.customer = customer;
        this.session = session;
    }

    public int getId() {
        return id;
    }
    public CustomerDTO getCustomer() {
        return customer;
    }
    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }
    public SessionDTO getSession() {
        return session;
    }
    public void setSession(SessionDTO session) {
        this.session = session;
    }

}   

