package com.finance.budget;

import com.finance.budget.exception.APIErros;
import com.finance.budget.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIErros handleBusinessException(BusinessException ex){
        return new APIErros(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIErros handleValidationException(MethodArgumentNotValidException ex){

        BindingResult bindingResult=ex.getBindingResult();
        return new APIErros (bindingResult);
    }

    @ExceptionHandler(ResponseStatusException.class)

    public ResponseEntity handleResponseStatusException(ResponseStatusException ex){
        return new ResponseEntity(new APIErros(ex), ex.getStatus());
    }
}
