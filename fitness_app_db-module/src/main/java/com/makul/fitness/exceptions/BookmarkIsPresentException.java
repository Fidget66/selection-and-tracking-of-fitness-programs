package com.makul.fitness.exceptions;

public class BookmarkIsPresentException extends RuntimeException{
    public BookmarkIsPresentException(){
        super("такая закладка уже есть!");
    }
}
