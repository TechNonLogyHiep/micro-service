package vti.dtn.api_gateway.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import vti.dtn.api_gateway.respone.AuthenticationRespone;

@RestControllerAdvice
public class HandleException extends ResponseEntityExceptionHandler {
    public ResponseEntity<Object> handleValidationException(ValidationException ex){
        AuthenticationRespone authenticationRespone = new AuthenticationRespone(HttpStatus.UNAUTHORIZED.value(),ex.getMessage());
        return new ResponseEntity<>(authenticationRespone,HttpStatus.UNAUTHORIZED);
    }
}
