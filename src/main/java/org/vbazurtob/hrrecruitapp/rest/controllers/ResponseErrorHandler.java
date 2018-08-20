package org.vbazurtob.hrrecruitapp.rest.controllers;

import com.sun.javafx.font.directwrite.RECT;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
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

import java.util.Date;

@RestController
@ControllerAdvice
public class ResponseErrorHandler extends ResponseEntityExceptionHandler {

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
        ErrorDetails errorDetails = new ErrorDetails(  ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity( errorDetails , HttpStatus.INTERNAL_SERVER_ERROR );
    }

}
