package com.parkee.techtest.exception;

import com.parkee.techtest.bean.GeneralResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    private Logger logger = LogManager.getLogger(this.getClass());
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GeneralResponseBean> handleBadRequestException(RuntimeException exception) {
        logger.error("Error: {}", exception.getMessage());  // logger for kind of exception message
        exception.printStackTrace(); // print stack trace
        GeneralResponseBean<String> response = new GeneralResponseBean<>(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
