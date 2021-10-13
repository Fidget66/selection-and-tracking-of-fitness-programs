package com.makul.fitness.exceptions;

public class IncorrectDataException  extends RuntimeException{
    public IncorrectDataException(String className){
        super("Введены некорректные данные для "+className);
    }
}