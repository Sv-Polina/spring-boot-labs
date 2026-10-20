package ru.urfu.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;

@Slf4j
@Service
public class ModifySourceRequestService implements ModifyRequestService {
    @Value("${service2.enabled:false}")
    private boolean service2Enabled;

    @Value("${service2.url:http://localhost:8095/feedback}")
    private String service2Url;

    @Override
    public void modify(Request request) {
        request.setSource("Service 1");
        log.info("Request: изменён source на '{}'", request.getSource());

        if (!service2Enabled) {
            log.info("Пересылка в Сервис 2 отключена (service2.enabled=false)");
            return;
        }

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        log.info("Пересылка request в Сервис 2: {}", service2Url);
        new RestTemplate().exchange(service2Url,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}
