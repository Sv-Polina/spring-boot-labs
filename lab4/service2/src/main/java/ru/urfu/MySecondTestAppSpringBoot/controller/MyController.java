package ru.urfu.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.urfu.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.urfu.MySecondTestAppSpringBoot.model.Codes;
import ru.urfu.MySecondTestAppSpringBoot.model.ErrorCodes;
import ru.urfu.MySecondTestAppSpringBoot.model.ErrorMessages;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;
import ru.urfu.MySecondTestAppSpringBoot.model.Response;
import ru.urfu.MySecondTestAppSpringBoot.service.ModifyResponseService;
import ru.urfu.MySecondTestAppSpringBoot.service.ValidationService;
import ru.urfu.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@RestController
public class MyController {
    private static final String UNSUPPORTED_UID = "123";

    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;

    @Autowired
    public MyController(ValidationService validationService,
                        @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
    }

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {
        Date receivedTime = new Date();
        log.info("request: {}", request);
        logDeliveryTime(request, receivedTime);

        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("response создан: {}", response);

        try {
            validationService.isValid(bindingResult);
            if (UNSUPPORTED_UID.equals(request.getUid())) {
                throw new UnsupportedCodeException("Не поддерживаемое значение uid: " + request.getUid());
            }
        } catch (ValidationFailedException e) {
            log.error("ValidationFailedException: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.VALIDATION_EXCEPTION);
            response.setErrorMessage(ErrorMessages.VALIDATION);
            log.info("response изменён (ошибка валидации): {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (UnsupportedCodeException e) {
            log.error("UnsupportedCodeException: {}", e.getMessage());
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNSUPPORTED_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNSUPPORTED);
            log.info("response изменён (не поддерживаемый uid): {}", response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Непредвиденная ошибка: {}", e.getMessage(), e);
            response.setCode(Codes.FAILED);
            response.setErrorCode(ErrorCodes.UNKNOWN_EXCEPTION);
            response.setErrorMessage(ErrorMessages.UNKNOWN);
            log.info("response изменён (непредвиденная ошибка): {}", response);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        modifyResponseService.modify(response);
        log.info("Итоговый response: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void logDeliveryTime(Request request, Date receivedTime) {
        if (request.getSystemTime() == null || request.getSystemTime().isBlank()) {
            log.warn("В request нет systemTime - время доставки не вычислить");
            return;
        }
        try {
            Date service1Time = DateTimeUtil.getCustomFormat().parse(request.getSystemTime());
            long diff = receivedTime.getTime() - service1Time.getTime();
            log.info("Время от получения request Сервисом 1 ({}) до получения Сервисом 2 ({}): {} мс",
                    request.getSystemTime(), DateTimeUtil.getCustomFormat().format(receivedTime), diff);
        } catch (ParseException e) {
            log.error("Не удалось разобрать systemTime '{}': {}", request.getSystemTime(), e.getMessage());
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleNotReadable(HttpMessageNotReadableException e) {
        log.error("Некорректное тело request: {}", e.getMostSpecificCause().getMessage());
        Response response = Response.builder()
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.FAILED)
                .errorCode(ErrorCodes.VALIDATION_EXCEPTION)
                .errorMessage(ErrorMessages.VALIDATION)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
