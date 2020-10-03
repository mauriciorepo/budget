package com.finance.budget.exception;

//import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

public class APIErros  {

    private List<String> erros;

    public APIErros(BindingResult bindResult){
        this.erros=new ArrayList<>();
        bindResult.getAllErrors().forEach(error -> {
            this.erros.add(error.getDefaultMessage());
        });
    }

    public APIErros(BusinessException ex){
        this.erros=Arrays.asList(ex.getMessage());

    }

    public APIErros(ResponseStatusException ex){
        this.erros=Arrays.asList(ex.getReason());
    }


}
