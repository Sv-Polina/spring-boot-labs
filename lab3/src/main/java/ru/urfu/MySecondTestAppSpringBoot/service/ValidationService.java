package ru.urfu.MySecondTestAppSpringBoot.service;

import org.springframework.validation.BindingResult;
import ru.urfu.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.urfu.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;

public interface ValidationService {
    void isValid(BindingResult bindingResult) throws ValidationFailedException;

    void isSupported(Request request) throws UnsupportedCodeException;
}
