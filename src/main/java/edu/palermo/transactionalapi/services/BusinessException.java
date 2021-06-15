package edu.palermo.transactionalapi.services;

public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
