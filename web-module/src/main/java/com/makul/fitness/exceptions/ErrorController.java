package com.makul.fitness.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IncorrectNumberOfDaysException.class)
    private String returnPageException (Model model, IncorrectNumberOfDaysException e){
        int status = 400;
        String messageException = e.getMessage();
        model.addAttribute("status",status );
        model.addAttribute("message", messageException);
        return "error";
    }

    @ExceptionHandler(HttpClientErrorException.class)
    private String returnPageException(Model model, HttpClientErrorException e) {
        int status = Integer.valueOf(e.getRawStatusCode());
        String messageException = e.getMessage().substring(e.getMessage().indexOf("message\":\"")+10,e.getMessage()
                .indexOf(",\"exception")-1).strip();;
        if (status>=500) messageException="Сервер не может обработать запрос!";
        model.addAttribute("status",status );
        model.addAttribute("message", messageException);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    private String returnPageException (Model model, Exception e, HttpStatus httpStatus){
        int status = httpStatus.value();
        String messageException;
        if (status>=400 && status<500) {messageException = "Не найдены данные согласно запросу!";
        } else if (status>=500) messageException="Сервер не может обработать запрос!";
        else messageException=e.getMessage();
        String exceptionType = e.getClass().getSimpleName();
        model.addAttribute("status",status );
        model.addAttribute("message", messageException);
        return "error";
    }
}
