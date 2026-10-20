package ru.urfu.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.urfu.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.urfu.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;

@Slf4j
@Service
public class RequestValidationService implements ValidationService {
    private static final String UNSUPPORTED_UID = "123";

    @Override
    public void isValid(BindingResult bindingResult) throws ValidationFailedException {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                log.error("Ошибка валидации поля '{}': {} (значение: {})",
                        error.getField(), error.getDefaultMessage(), error.getRejectedValue());
            }
            throw new ValidationFailedException(bindingResult.getFieldError().toString());
        }
    }

    @Override
    public void isSupported(Request request) throws UnsupportedCodeException {
        if (UNSUPPORTED_UID.equals(request.getUid())) {
            throw new UnsupportedCodeException("uid = " + UNSUPPORTED_UID + " не поддерживается");
        }
    }
}
