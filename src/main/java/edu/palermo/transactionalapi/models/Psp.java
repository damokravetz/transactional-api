package edu.palermo.transactionalapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.palermo.transactionalapi.services.BusinessException;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "psps")
@JsonIgnoreProperties(value = {"id"})
public class Psp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String razonSocial;
    private String cuit;
    private String username;
    private String password;
    @Column(length = 4)
    private String pspCode;
    private Integer tipoPSP;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    @Transient
    private String token;
    @Transient
    private String cbu;

    public Psp(String razonSocial, String cuit, String username, String password, Account account) {
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.username = username;
        this.password = password;
        this.account= account;
        this.tipoPSP=1;
    }

    public Psp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPspCode() {
        return pspCode;
    }

    public void setPspCode(String pspCode) {
        this.pspCode = pspCode;
    }

    public Integer getTipoPSP() {
        return tipoPSP;
    }

    public void setTipoPSP(Integer tipoPSP) {
        this.tipoPSP = tipoPSP;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
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

    public Boolean isCuitValid(){
        Boolean res=false;
        try{
            Long value= Long.valueOf(this.cuit);
            if(this.cuit.length()==11){
                res=true;
            }
        }catch (NumberFormatException ex){
            res=false;
        }
        return res;
    }
}
