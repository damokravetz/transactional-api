package edu.palermo.transactionalapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "cvus")
@JsonIgnoreProperties(value = {"id"})
public class Cvu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cvu;
    @ManyToOne
    @JoinColumn(name = "pspId", referencedColumnName = "id")
    private Psp psp;
    private String alias;
    private int moneda;

    public Cvu(){

    }

    public Cvu(String cvu, Psp psp) {
        this.cvu = cvu;
        this.psp = psp;
        this.moneda=032;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public Psp getPsp() {
        return psp;
    }

    public void setPsp(Psp psp) {
        this.psp = psp;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }
}
