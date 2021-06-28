package edu.palermo.transactionalapi.models;

public class TransferRequest {
    private String userPspId;
    private String cvuAliasDestination;
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

    public String getCvuAliasDestination() {
        return cvuAliasDestination;
    }

    public void setCvuAliasDestination(String cvuAliasDestination) {
        this.cvuAliasDestination = cvuAliasDestination;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
