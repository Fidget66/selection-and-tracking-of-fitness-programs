package com.makul.fitness.exceptions;

public class ScheduleIsPresentException  extends RuntimeException {

    public ScheduleIsPresentException(){
        super("Расписание для данной программы уже составлено!");
    }
}
