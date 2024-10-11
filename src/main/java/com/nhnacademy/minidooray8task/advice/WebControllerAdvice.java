package com.nhnacademy.minidooray8task.advice;

import com.nhnacademy.minidooray8task.exception.AccountNotFoundException;
import com.nhnacademy.minidooray8task.exception.ProjectAlreadyExistsException;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler({ProjectNotFoundException.class, AccountNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProjectAlreadyExistsException.class})
    public ResponseEntity<String> handleAlreadyExistsException(Exception ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.CONFLICT);
    }
}
