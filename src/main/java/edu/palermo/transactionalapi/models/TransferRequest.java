package edu.palermo.transactionalapi.models;

public class TransferRequest {
    private String cvuOrigin;
    private String cvuDestination;
    //private String aliasDestination;
    private Double amount;

    public TransferRequest() {
    }

    public String getCvuOrigin() {
        return cvuOrigin;
    }

    public void setCvuOrigin(String cvuOrigin) {
        this.cvuOrigin = cvuOrigin;
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
