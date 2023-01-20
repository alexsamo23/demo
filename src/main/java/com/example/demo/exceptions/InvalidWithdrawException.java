package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_MODIFIED)
public class InvalidWithdrawException extends Exception{
    private String message;
    public InvalidWithdrawException(String message){
        super(message);
        this.message=message;
    }
}
