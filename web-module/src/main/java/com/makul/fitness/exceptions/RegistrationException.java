package com.makul.fitness.exceptions;

public class RegistrationException extends RuntimeException{
    public RegistrationException(){
        super("Регистрация не удалась, попробуйте еще раз");
    }
}