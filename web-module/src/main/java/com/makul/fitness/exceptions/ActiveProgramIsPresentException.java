package com.makul.fitness.exceptions;

public class ActiveProgramIsPresentException extends RuntimeException{
    public ActiveProgramIsPresentException(){
        super("У вас нет незавершенных программ");
    }
}
