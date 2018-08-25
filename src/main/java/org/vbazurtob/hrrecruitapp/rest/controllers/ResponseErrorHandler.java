package org.vbazurtob.hrrecruitapp.rest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.vbazurtob.hrrecruitapp.rest.lib.common.ErrorDetails;
import org.vbazurtob.hrrecruitapp.rest.lib.common.RecordAlreadyExists;


@RestController
@ControllerAdvice
public class ResponseErrorHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ResponseErrorHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetails err = new ErrorDetails("Validation input data failed", ex.getBindingResult().getAllErrors().toString());

        return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecordAlreadyExists.class)
    public final ResponseEntity<Object> handleRecordAlreadyExists(RecordAlreadyExists ex, WebRequest request){
        return new ResponseEntity(new ErrorDetails(ex.getMessage(), request.getDescription(false)), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error(ex.getMessage());
        ErrorDetails errorDetails = new ErrorDetails(  ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity( errorDetails , HttpStatus.INTERNAL_SERVER_ERROR );
    }

}
