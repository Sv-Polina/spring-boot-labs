package ru.urfu.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.urfu.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.urfu.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;

import java.util.stream.Collectors;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {
    private static final String UNSUPPORTED_UID = "123";

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                log.error("bindingResult error: field '{}' = '{}': {}",
                        error.getField(), error.getRejectedValue(), error.getDefaultMessage());
            }
            String message = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining("; "));
            throw new ValidationFailedException(message);
        }
        log.info("Validation passed");
    }

    @Override
    public void isSupported(Request request) throws UnsupportedCodeException {
        if (UNSUPPORTED_UID.equals(request.getUid())) {
            throw new UnsupportedCodeException("uid " + UNSUPPORTED_UID + " is not supported");
        }
    }
}
