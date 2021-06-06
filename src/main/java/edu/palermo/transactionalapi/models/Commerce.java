package edu.palermo.transactionalapi.models;

import javax.persistence.*;

@Entity
@Table(name = "commerces")
public class Commerce {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String cuit;
    private String name;

    public Commerce(){

    }

    public Commerce(String cuit, String name){
        this.cuit=cuit;
        this.name=name;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
