package edu.palermo.transactionalapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "transfers")
@JsonIgnoreProperties(value = { "id" })
public class Transfer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "accountOriginId", referencedColumnName = "id")
    private Account accountOrigin;
    @ManyToOne
    @JoinColumn(name = "accountDestinationId", referencedColumnName = "id")
    private Account accountDestination;
    @ManyToOne
    @JoinColumn(name = "userOriginId", referencedColumnName = "id")
    private User userOrigin;
    @ManyToOne
    @JoinColumn(name = "userDestinationId", referencedColumnName = "id")
    private User userDestination;
    private double amount;

    public Transfer(Account accountOrigin, Account accountDestination, User userOrigin, User userDestination, double amount) {
        this.accountOrigin = accountOrigin;
        this.accountDestination = accountDestination;
        this.userOrigin = userOrigin;
        this.userDestination = userDestination;
        this.amount = amount;
    }

    public User getUserOrigin() {
        return userOrigin;
    }

    public void setUserOrigin(User userOrigin) {
        this.userOrigin = userOrigin;
    }

    public User getUserDestination() {
        return userDestination;
    }

    public void setUserDestination(User userDestination) {
        this.userDestination = userDestination;
    }

    public Account getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOrigin(Account accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public Account getAccountDestination() {
        return accountDestination;
    }

    public void setAccountDestination(Account accountDestination) {
        this.accountDestination = accountDestination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
