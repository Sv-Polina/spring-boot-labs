package ru.urfu.MySecondTestAppSpringBoot.exception;

public class NotManagerException extends RuntimeException {
    public NotManagerException(String message) {
        super(message);
    }
}
