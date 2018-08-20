package org.vbazurtob.hrrecruitapp.rest.model.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.vbazurtob.hrrecruitapp.rest.lib.common.ErrorDetails;

@RestController
@ControllerAdvice
public class ResponseErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDetails err = new ErrorDetails("Validation input data failed", ex.getBindingResult().getAllErrors().toString());

        return new ResponseEntity(err, HttpStatus.BAD_REQUEST);
//                super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
}
