package io.github.jpportocampos.rest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
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
}