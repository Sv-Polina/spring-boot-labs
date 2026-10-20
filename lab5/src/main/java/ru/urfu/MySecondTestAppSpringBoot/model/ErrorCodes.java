package ru.urfu.MySecondTestAppSpringBoot.model;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

public enum ErrorCodes {
    EMPTY("", HttpStatus.OK),
    VALIDATION_EXCEPTION("ValidationException", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_EXCEPTION("UnsupportedCodeException", HttpStatus.BAD_REQUEST),
    UNKNOWN_EXCEPTION("UnknownException", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String name;

    private final HttpStatus httpStatus;

    ErrorCodes(String name, HttpStatus httpStatus) {
        this.name = name;
        this.httpStatus = httpStatus;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String toString() {
        return name;
    }

}
