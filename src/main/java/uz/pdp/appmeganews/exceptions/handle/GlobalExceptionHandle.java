package uz.pdp.appmeganews.exceptions.handle;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.pdp.appmeganews.exceptions.BadRequestException;
import uz.pdp.appmeganews.exceptions.NotFoundException;
import uz.pdp.appmeganews.exceptions.NotReadyForWorkException;
import uz.pdp.appmeganews.payload.ApiResultDTO;

//@RestControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResultDTO.error(exception.getFieldErrors()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handle(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResultDTO.error(exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handle(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResultDTO.error(exception.getMessage()));
    }

    @ExceptionHandler(NotReadyForWorkException.class)
    public ResponseEntity<?> handle(NotReadyForWorkException exception) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ApiResultDTO.error(exception.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handle(DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(ApiResultDTO.error(exception));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception exception) {
        return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).body(ApiResultDTO.error(exception.getMessage()));
    }
}
