package edu.palermo.transactionalapi.models;

public class TransferRequest {
    private String userPspId;
    private String cvuDestination;
    //private String aliasDestination;
    private Double amount;

    public TransferRequest() {
    }

    public String getUserPspId() {
        return userPspId;
    }

    public void setUserPspId(String userPspId) {
        this.userPspId = userPspId;
    }

    public String getCvuDestination() {
        return cvuDestination;
    }

    public void setCvuDestination(String cvuDestination) {
        this.cvuDestination = cvuDestination;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
