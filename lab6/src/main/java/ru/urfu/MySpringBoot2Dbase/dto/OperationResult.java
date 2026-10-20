package ru.urfu.MySpringBoot2Dbase.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationResult<T> {
    private final boolean success;
    private final String message;
    private final T data;
    private final LocalDateTime timestamp;

    public static <T> OperationResult<T> ok(String message, T data) {
        return new OperationResult<>(true, message, data, LocalDateTime.now().withNano(0));
    }

    public static <T> OperationResult<T> fail(String message) {
        return new OperationResult<>(false, message, null, LocalDateTime.now().withNano(0));
    }
}
