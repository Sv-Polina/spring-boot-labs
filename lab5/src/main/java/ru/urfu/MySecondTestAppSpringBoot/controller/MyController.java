package ru.urfu.MySecondTestAppSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;
import ru.urfu.MySecondTestAppSpringBoot.model.Response;
import ru.urfu.MySecondTestAppSpringBoot.service.FeedbackService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyController {
    private final FeedbackService feedbackService;

    @PostMapping(value = "/feedback")
    public ResponseEntity<Response> feedback(@Valid @RequestBody Request request,
                                             BindingResult bindingResult) {
        log.info("request: {}", request);
        Response response = feedbackService.process(request, bindingResult);
        return new ResponseEntity<>(response, response.getErrorCode().getHttpStatus());
    }
}
