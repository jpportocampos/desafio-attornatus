package io.github.jpportocampos.rest;


import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    private HttpStatus status;
    private List<String> errors;

    public ApiErrors(HttpStatus status, List<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
    }

    public ApiErrors(HttpStatus status, String error) {
        super();
        this.status = status;
        this.errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}