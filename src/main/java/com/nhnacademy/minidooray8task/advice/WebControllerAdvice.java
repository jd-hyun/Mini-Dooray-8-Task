package com.nhnacademy.minidooray8task.advice;

import com.nhnacademy.minidooray8task.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebControllerAdvice {

    @ExceptionHandler({ProjectNotFoundException.class,
            AccountNotFoundException.class,
            CommentNotFoundException.class,
            MilestoneNotFoundException.class,
            TagNotFoundException.class,
            TaskNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProjectAlreadyExistsException.class,
            TagAlreadyException.class})
    public ResponseEntity<String> handleAlreadyExistsException(Exception ex) {
        return new ResponseEntity<>(ex.toString(), HttpStatus.CONFLICT);
    }
}
