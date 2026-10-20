package ru.urfu.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;

@Slf4j
@Service
@Qualifier("ModifySourceRequestService")
public class ModifySourceRequestService implements ModifyRequestService {
    public static final String SOURCE = "MySecondTestAppSpringBoot (Service 1)";

    @Override
    public void modify(Request request) {
        String oldSource = request.getSource();
        request.setSource(SOURCE);
        log.info("request: изменено поле source: '{}' -> '{}'", oldSource, request.getSource());
    }
}
