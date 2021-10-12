package com.makul.fitness.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoEntityException extends RuntimeException {

    public NoEntityException(String className){
        super("Такой записи для " +className+" в базе данных не существует");
        log.error("Ошибка поиска данных в {} ",  className);
    }
}
