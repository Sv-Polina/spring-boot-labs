package ru.urfu.MySecondTestAppSpringBoot.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {

    /**
     * Уникальный идентификатор сообщения
     */
    private String uid;

    /**
     * Уникальный идентификатор операции
     */
    private String operationUid;

    /**
     * Время создания ответного сообщения
     */
    private String systemTime;

    /**
     * Код результата обработки: «success» или «failed»
     */
    private Codes code;

    /**
     * Рассчитанная годовая премия
     */
    private Double annualBonus;

    /**
     * Рассчитанная квартальная премия (только для управленцев, иначе null)
     */
    private Double quarterlyBonus;

    /**
     * Код ошибки: «UnsupportedCodeException», «ValidationException», «UnknownException»
     */
    private ErrorCodes errorCode;

    /**
     * Сообщение об ошибке: «Не поддерживаемая ошибка», «Ошибка валидации», «Произошла непредвиденная ошибка»
     */
    private ErrorMessages errorMessage;

}
