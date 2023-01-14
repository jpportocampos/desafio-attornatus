package io.github.jpportocampos.exception;

import io.github.jpportocampos.rest.ApiErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RegraNegocioExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiErrors> handleRegraNegocioException(RegraNegocioException exception, WebRequest request) {

        ApiErrors errorMessage = new ApiErrors(HttpStatus.BAD_REQUEST, exception.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), errorMessage.getStatus());

    }
}
