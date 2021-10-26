package com.makul.fitness.exceptions;

public class BookmarkIsPresentException extends RuntimeException{
    public BookmarkIsPresentException(){
        super("Такая закладка уже есть у Вас.");
    }
}
