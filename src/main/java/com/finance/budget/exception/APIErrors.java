package com.finance.budget.exception;

//import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

public class APIErrors {

    private List<String> errors;

    public APIErrors(BindingResult bindResult){
        this.errors=new ArrayList<>();
        bindResult.getAllErrors().forEach(error -> {
            this.errors.add(error.getDefaultMessage());
        });
    }

    public APIErrors(BusinessException ex){
        this.errors=Arrays.asList(ex.getMessage());

    }

    public APIErrors(ResponseStatusException ex){
        this.errors=Arrays.asList(ex.getReason());
    }

    public List<String> getErrors() {
        return errors;
    }
}
