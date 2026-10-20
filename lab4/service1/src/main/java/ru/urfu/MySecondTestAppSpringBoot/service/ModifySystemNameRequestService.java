package ru.urfu.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;
import ru.urfu.MySecondTestAppSpringBoot.model.Response;
import ru.urfu.MySecondTestAppSpringBoot.model.Systems;

@Slf4j
@Service
@Qualifier("ModifySystemNameRequestService")
public class ModifySystemNameRequestService implements ModifyRequestService {
    private final RestTemplate restTemplate;
    private final String service2Url;

    public ModifySystemNameRequestService(RestTemplateBuilder restTemplateBuilder,
                                          @Value("${service2.url}") String service2Url) {
        this.restTemplate = restTemplateBuilder.build();
        this.service2Url = service2Url;
    }

    @Override
    public void modify(Request request) {
        request.setSystemName(Systems.SERVICE_1);
        log.info("request: изменено поле systemName = '{}'", request.getSystemName());

        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        log.info("Отправка модифицированного request в Сервис 2 ({}): {}", service2Url, request);
        ResponseEntity<Response> service2Response = restTemplate.exchange(service2Url,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
        log.info("Ответ Сервиса 2: HTTP {} {}", service2Response.getStatusCode().value(),
                service2Response.getBody());
    }
}
