package edu.palermo.transactionalapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "cashins")
@JsonIgnoreProperties(value = { "id" })
public class CashIn {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Psp psp;
    @ManyToOne
    @JoinColumn(name = "creditCardId", referencedColumnName = "id")
    private CreditCard creditCard;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
    private Double amount;
    private String idTransaction;
    private String date;

    public CashIn(Psp psp, CreditCard creditCard, User user, Double amount, String idTransaction, String date) {
        this.psp = psp;
        this.creditCard = creditCard;
        this.user = user;
        this.amount=amount;
        this.idTransaction=idTransaction;
        this.date=date;
    }

    public CashIn(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Psp getAccount() {
        return psp;
    }

    public void setAccount(Psp psp) {
        this.psp = psp;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Psp getPsp() {
        return psp;
    }

    public void setPsp(Psp psp) {
        this.psp = psp;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
