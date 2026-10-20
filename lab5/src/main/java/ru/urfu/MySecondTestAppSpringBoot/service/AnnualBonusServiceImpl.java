package ru.urfu.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.urfu.MySecondTestAppSpringBoot.exception.NotManagerException;
import ru.urfu.MySecondTestAppSpringBoot.model.Positions;

import java.time.Clock;
import java.time.Year;

@Service
public class AnnualBonusServiceImpl implements AnnualBonusService {
    private static final int QUARTERS_IN_YEAR = 4;

    private final Clock clock;

    public AnnualBonusServiceImpl() {
        this(Clock.systemDefaultZone());
    }

    public AnnualBonusServiceImpl(Clock clock) {
        this.clock = clock;
    }

    @Override
    public double calculate(Positions positions, double salary, double bonus, int workDays) {
        checkWorkDays(workDays);
        int daysInYear = getDaysInYear(Year.now(clock).getValue());
        return salary * bonus * daysInYear * positions.getPositionCoefficient() / workDays;
    }

    @Override
    public double calculateQuarterlyBonus(Positions positions, double salary, double bonus, int workDays) {
        if (!positions.isManager()) {
            throw new NotManagerException(
                    "Квартальная премия положена только управленцам, должность " + positions + " не является управленческой");
        }
        return calculate(positions, salary, bonus, workDays) / QUARTERS_IN_YEAR;
    }

    static int getDaysInYear(int year) {
        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
        return isLeapYear ? 366 : 365;
    }

    private static void checkWorkDays(int workDays) {
        if (workDays <= 0) {
            throw new IllegalArgumentException("Количество рабочих дней должно быть больше 0");
        }
    }
}
