package edu.palermo.transactionalapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "accounts")
@JsonIgnoreProperties(value = {"id"})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cbu;
    private Double amount;
    @OneToOne(mappedBy = "account")
    private Psp psp;

    public Account(){

    }

    public Account(String cbu, Double amount) {
        this.cbu = cbu;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean isCbuValid(){
        Boolean res=false;
        String regex="^[0-9]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(this.cbu);
        if(this.cbu.length() == 22 && m.matches()) {
            res=true;
        }
        return res;
    }

    public Boolean isAmountValid(){
        Boolean res= false;
        if(amount>=0){
            res=true;
        }
        return res;
    }
}
