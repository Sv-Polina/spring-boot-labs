package ru.urfu.MySpringBoot2Dbase.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.urfu.MySpringBoot2Dbase.dto.OperationResult;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<OperationResult<Void>> handleValidation(MethodArgumentNotValidException e) {
        String errors = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return ResponseEntity.badRequest().body(OperationResult.fail("Ошибка валидации — " + errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<OperationResult<Void>> handleBadJson(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(OperationResult.fail("Некорректное тело запроса (ожидается JSON)"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<OperationResult<Void>> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.badRequest().body(OperationResult.fail(
                "Некорректное значение параметра '" + e.getName() + "': " + e.getValue()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<OperationResult<Void>> handleDb(DataAccessException e) {
        log.error("Database error", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(OperationResult.fail("Ошибка базы данных: " + e.getMostSpecificCause().getMessage()));
    }
}
