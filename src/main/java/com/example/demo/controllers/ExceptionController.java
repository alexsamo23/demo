package com.example.demo.controllers;

import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = InvalidWithdrawException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String
    handleInvalidWithdrawException(InvalidWithdrawException ex, Model model)
    {
        model.addAttribute("ex",ex);
        return "error";
    }
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String//ErrorResponse
    handleResourceNotFoundException(ResourceNotFoundException ex,Model model)
    {
        model.addAttribute("ex",ex);
        return "error"; //new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

}
