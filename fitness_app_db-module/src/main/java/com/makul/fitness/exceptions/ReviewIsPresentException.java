package com.makul.fitness.exceptions;

public class ReviewIsPresentException extends RuntimeException{
    public ReviewIsPresentException(){
        super("Вы уже оставили отзыв по данной программе");
    }
}
