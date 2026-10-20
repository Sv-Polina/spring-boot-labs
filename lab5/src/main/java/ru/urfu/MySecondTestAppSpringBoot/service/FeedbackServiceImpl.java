package ru.urfu.MySecondTestAppSpringBoot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.urfu.MySecondTestAppSpringBoot.exception.UnsupportedCodeException;
import ru.urfu.MySecondTestAppSpringBoot.exception.ValidationFailedException;
import ru.urfu.MySecondTestAppSpringBoot.model.Codes;
import ru.urfu.MySecondTestAppSpringBoot.model.ErrorCodes;
import ru.urfu.MySecondTestAppSpringBoot.model.ErrorMessages;
import ru.urfu.MySecondTestAppSpringBoot.model.Request;
import ru.urfu.MySecondTestAppSpringBoot.model.Response;
import ru.urfu.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Slf4j
@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final ValidationService validationService;
    private final ModifyResponseService modifyResponseService;
    private final ModifyRequestService modifyRequestService;
    private final AnnualBonusService annualBonusService;

    public FeedbackServiceImpl(ValidationService validationService,
                               @Qualifier("ModifySystemTimeResponseService") ModifyResponseService modifyResponseService,
                               ModifyRequestService modifyRequestService,
                               AnnualBonusService annualBonusService) {
        this.validationService = validationService;
        this.modifyResponseService = modifyResponseService;
        this.modifyRequestService = modifyRequestService;
        this.annualBonusService = annualBonusService;
    }

    @Override
    public Response process(Request request, BindingResult bindingResult) {
        Response response = createResponse(request);

        try {
            validationService.isValid(bindingResult);
            validationService.isSupported(request);
            calculateBonuses(request, response);
            modifyResponseService.modify(response);
            modifyRequestService.modify(request);
        } catch (ValidationFailedException e) {
            return fail(response, ErrorCodes.VALIDATION_EXCEPTION, ErrorMessages.VALIDATION, e);
        } catch (UnsupportedCodeException e) {
            return fail(response, ErrorCodes.UNSUPPORTED_EXCEPTION, ErrorMessages.UNSUPPORTED, e);
        } catch (Exception e) {
            return fail(response, ErrorCodes.UNKNOWN_EXCEPTION, ErrorMessages.UNKNOWN, e);
        }

        log.info("Итоговый response: {}", response);
        return response;
    }

    private Response createResponse(Request request) {
        Response response = Response.builder()
                .uid(request.getUid())
                .operationUid(request.getOperationUid())
                .systemTime(DateTimeUtil.getCustomFormat().format(new Date()))
                .code(Codes.SUCCESS)
                .errorCode(ErrorCodes.EMPTY)
                .errorMessage(ErrorMessages.EMPTY)
                .build();
        log.info("response создан: {}", response);
        return response;
    }

    private void calculateBonuses(Request request, Response response) {
        response.setAnnualBonus(annualBonusService.calculate(
                request.getPosition(), request.getSalary(), request.getBonus(), request.getWorkDays()));
        log.info("response: годовая премия = {}", response.getAnnualBonus());

        if (request.getPosition().isManager()) {
            response.setQuarterlyBonus(annualBonusService.calculateQuarterlyBonus(
                    request.getPosition(), request.getSalary(), request.getBonus(), request.getWorkDays()));
            log.info("response: квартальная премия = {}", response.getQuarterlyBonus());
        } else {
            log.info("Квартальная премия не рассчитывается: {} не является управленцем", request.getPosition());
        }
    }

    private Response fail(Response response, ErrorCodes errorCode, ErrorMessages errorMessage, Exception e) {
        log.error("{}: {}", errorCode, e.getMessage());
        response.setCode(Codes.FAILED);
        response.setErrorCode(errorCode);
        response.setErrorMessage(errorMessage);
        log.info("response изменён: {}", response);
        return response;
    }
}
