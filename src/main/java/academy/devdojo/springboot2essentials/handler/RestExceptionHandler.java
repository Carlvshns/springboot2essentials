package academy.devdojo.springboot2essentials.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import academy.devdojo.springboot2essentials.exception.BadRequestException;
import academy.devdojo.springboot2essentials.exception.BadRequestExceptionDetails;
import academy.devdojo.springboot2essentials.exception.ExceptionDetails;
import academy.devdojo.springboot2essentials.exception.ValidationExceptionDetails;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(BadRequestException bre){
        return new ResponseEntity<>(BadRequestExceptionDetails.Builder.newBuilder()
        .timestamp(LocalDateTime.now())
        .status(HttpStatus.BAD_REQUEST.value())
        .title("Bad Request Exception, Check the Documentation")
        .details(bre.getMessage())
        .developerMessage(bre.getClass().getName())
        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String field = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        return new ResponseEntity<>(ValidationExceptionDetails.Builder.newBuilder()
        .timestamp(LocalDateTime.now())
        .status(status.value())
        .title("Bad Request Exception, invalid Fields")
        .details(exception.getMessage())
        .developerMessage(exception.getClass().getName())
        .field(field)
        .fieldMessage(fieldMessage)
        .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected  ResponseEntity<Object> handleExceptionInternal
    (Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionDetails exceptionDetails = ExceptionDetails.Builder.newBuilder()
        .timestamp(LocalDateTime.now())
        .status(status.value())
        .title(ex.getCause().getMessage())
        .details(ex.getMessage())
        .developerMessage(ex.getClass().getName())
        .build();
        return new ResponseEntity<>(exceptionDetails, headers, status);
    }
}
