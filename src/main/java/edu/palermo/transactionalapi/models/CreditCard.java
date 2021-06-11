package edu.palermo.transactionalapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "creditcards")
@JsonIgnoreProperties(value = { "id" })
public class CreditCard {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String verificationCode;
    private String expirationDate;
    private String name;
    private String number;
    private String type;

    public CreditCard(){

    }

    public CreditCard(String verificationCode, String expirationDate, String name, String number, String type) {
        this.verificationCode = verificationCode;
        this.expirationDate = expirationDate;
        this.name = name;
        this.number = number;
        this.type = type;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
