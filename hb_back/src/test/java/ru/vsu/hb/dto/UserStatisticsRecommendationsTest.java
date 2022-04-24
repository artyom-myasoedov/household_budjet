package ru.vsu.hb.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserStatisticsRecommendationsTest {

    @Test
    public void getCurrMonthExpensesSumTest() {
        var stat = new UserStatisticsRecommendations();
        stat.setCurrMonthExpenses(Map.of("1", BigDecimal.ONE, "2", BigDecimal.TEN, "3", BigDecimal.valueOf(5.3)));
        assertEquals(BigDecimal.valueOf(16.3), stat.getCurrMonthExpensesSum());
    }

    @Test
    public void getCurrMonthPercentsTest() {
        var stat = new UserStatisticsRecommendations();
        stat.setCurrMonthExpenses(Map.of("1", BigDecimal.ONE, "2", BigDecimal.ONE, "3", BigDecimal.ZERO));
        var temp = Map.of("1", BigDecimal.valueOf(50).setScale(6, RoundingMode.HALF_UP), "2", BigDecimal.valueOf(50).setScale(6, RoundingMode.HALF_UP), "3", BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP));
        assertEquals(temp, stat.getCurrMothPercents());
    }

}