package edu.palermo.transactionalapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(value = { "id" })
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String dni;
    private String userPspId;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cvuId", referencedColumnName = "id")
    private Cvu cvu;

    @Transient
    String alias;

    public User() {
    }

    public User(String name, String dni, String userPspId) {
        this.name = name;
        this.dni = dni;
        this.userPspId= userPspId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Cvu getCvu() {
        return cvu;
    }

    public void setCvu(Cvu cvu) {
        this.cvu = cvu;
    }

    public String getUserPspId() {
        return userPspId;
    }

    public void setUserPspId(String userPspId) {
        this.userPspId = userPspId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean isUserPspIdValid(){
        Boolean res=false;
        try{
            Long value= Long.valueOf(this.userPspId);
            if(this.userPspId.length()==12){
                res=true;
            }
        }catch (NumberFormatException ex){
            res=false;
        }
        return res;
    }

    public Boolean isUserDniValid(){
        Boolean res=false;
        try{
            Integer value= Integer.valueOf(this.dni);
            if(this.dni.length()==8){
                res=true;
            }
        }catch (NumberFormatException ex){
            res=false;
        }
        return res;
    }

}
