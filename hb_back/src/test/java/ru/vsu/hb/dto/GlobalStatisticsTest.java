package ru.vsu.hb.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalStatisticsTest {

    @Test
    public void getPrevMonthSumOutTest() {
        var stat = new GlobalStatistics();
        stat.setPrevMonthExpenses(Map.of("1", BigDecimal.ONE, "2", BigDecimal.ONE, "3", BigDecimal.ZERO));
        assertEquals(BigDecimal.valueOf(2), stat.getPrevMonthSumOut());
    }

    @Test
    public void getPrevMonthPercents() {
        var stat = new GlobalStatistics();
        stat.setCurrMonthExpenses(Map.of("1", BigDecimal.ONE, "2", BigDecimal.ONE, "3", BigDecimal.ZERO));
        var temp = Map.of("1", BigDecimal.valueOf(50).setScale(6, RoundingMode.HALF_UP), "2", BigDecimal.valueOf(50).setScale(6, RoundingMode.HALF_UP), "3", BigDecimal.ZERO.setScale(6, RoundingMode.HALF_UP));
        assertEquals(temp, stat.getCurrMothPercents());
    }

}