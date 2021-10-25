package com.makul.fitness.exceptions;

public class ActiveProgramNotPresentException extends RuntimeException{
    public ActiveProgramNotPresentException(){
        super("У вас нет незавершенных программ");
    }
}
