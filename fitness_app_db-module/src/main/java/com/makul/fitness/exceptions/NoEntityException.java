package com.makul.fitness.exceptions;

public class NoEntityException extends RuntimeException {
    public NoEntityException(String className){
        super("Такой записи для " +className+" в базе данных не существует");
    }
}
