package com.xpto.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice //This annotation makes this class being invoked when any exception, thrown from a Controller, occurs. Then it will look if there exists a correspondent/specialized exception.
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex, WebRequest request){
        ProjectIdResponseException projectIdResponseException = new ProjectIdResponseException(ex.getMessage());
        return new ResponseEntity<>(projectIdResponseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request){
        ProjectNotFoundResponseException projectNotFoundResponseException = new ProjectNotFoundResponseException(ex.getMessage());
        return new ResponseEntity<>(projectNotFoundResponseException, HttpStatus.BAD_REQUEST);
    }

}
