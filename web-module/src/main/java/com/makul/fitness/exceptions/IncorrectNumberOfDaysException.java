package com.makul.fitness.exceptions;

public class IncorrectNumberOfDaysException extends RuntimeException{
    public IncorrectNumberOfDaysException(){
        super("Выберите различные дни для расписания");
    }
}