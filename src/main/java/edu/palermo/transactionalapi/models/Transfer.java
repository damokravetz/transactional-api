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
    private Psp pspOrigin;
    @ManyToOne
    @JoinColumn(name = "accountDestinationId", referencedColumnName = "id")
    private Psp pspDestination;
    @ManyToOne
    @JoinColumn(name = "userOriginId", referencedColumnName = "id")
    private User userOrigin;
    @ManyToOne
    @JoinColumn(name = "userDestinationId", referencedColumnName = "id")
    private User userDestination;
    private double amount;

    public Transfer(Psp pspOrigin, Psp pspDestination, User userOrigin, User userDestination, double amount) {
        this.pspOrigin = pspOrigin;
        this.pspDestination = pspDestination;
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

    public Psp getAccountOrigin() {
        return pspOrigin;
    }

    public void setAccountOrigin(Psp pspOrigin) {
        this.pspOrigin = pspOrigin;
    }

    public Psp getAccountDestination() {
        return pspDestination;
    }

    public void setAccountDestination(Psp pspDestination) {
        this.pspDestination = pspDestination;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
