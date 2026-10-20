package ru.urfu.MySecondTestAppSpringBoot.service;

import org.junit.jupiter.api.Test;
import ru.urfu.MySecondTestAppSpringBoot.exception.NotManagerException;
import ru.urfu.MySecondTestAppSpringBoot.model.Positions;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

class AnnualBonusServiceImplTest {
    private static final Clock CLOCK_2023 = Clock.fixed(Instant.parse("2023-06-15T10:00:00Z"), ZoneOffset.UTC);

    @Test
    void calculate() {
        Positions position = Positions.HR;
        double bonus = 2.0;
        int workDays = 243;
        double salary = 100000.00;

        double result = new AnnualBonusServiceImpl(CLOCK_2023).calculate(position, salary, bonus, workDays);

        double expected = 360493.8271604938;
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void calculateQuarterlyBonus() {
        Positions position = Positions.TL;
        double bonus = 1.5;
        int workDays = 247;
        double salary = 200000.00;

        double result = new AnnualBonusServiceImpl(CLOCK_2023)
                .calculateQuarterlyBonus(position, salary, bonus, workDays);

        double expected = 200000.00 * 1.5 * 365 * 2.6 / 247 / 4;
        assertThat(result).isCloseTo(expected, within(1e-6));
    }

    @Test
    void calculateQuarterlyBonusNotManager() {
        AnnualBonusServiceImpl service = new AnnualBonusServiceImpl(CLOCK_2023);

        assertThatThrownBy(() -> service.calculateQuarterlyBonus(Positions.DEV, 150000, 1.0, 247))
                .isInstanceOf(NotManagerException.class);
    }
}
