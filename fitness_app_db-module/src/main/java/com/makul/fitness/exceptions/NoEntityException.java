package com.makul.fitness.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoEntityException extends RuntimeException {
    public NoEntityException(String className){
        super( String.format("Такой записи для %s в базе данных не существует", className));
        log.error("Такой записи для {} в базе данных не существует", className);
    }
}
