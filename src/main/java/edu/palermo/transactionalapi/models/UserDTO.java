package edu.palermo.transactionalapi.models;

public class UserDTO {
    private Long id;
    private String name;
    private String dni;
    private String cvu;
    private String userPspId;
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getUserPspId() {
        return userPspId;
    }

    public void setUserPspId(String userPspId) {
        this.userPspId = userPspId;
    }
}
