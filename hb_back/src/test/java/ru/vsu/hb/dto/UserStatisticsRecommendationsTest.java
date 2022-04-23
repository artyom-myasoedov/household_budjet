package ru.vsu.hb.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserStatisticsRecommendationsTest {

    @Test
    public void getCurrMonthExpensesSumTest() {
        var stat = new UserStatisticsRecommendations();
        stat.setCurrMonthExpenses(Map.of("1", BigDecimal.ONE, "2", BigDecimal.TEN, "3", BigDecimal.valueOf(5.3)));
        assertEquals(BigDecimal.valueOf(16.3), stat.getCurrMonthExpensesSum());
    }

}