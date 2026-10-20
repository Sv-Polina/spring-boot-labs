package ru.urfu.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;
import ru.urfu.MySecondTestAppSpringBoot.model.Response;

@Service
public interface FeedbackService {
    Response process(Request request, BindingResult bindingResult);

}
