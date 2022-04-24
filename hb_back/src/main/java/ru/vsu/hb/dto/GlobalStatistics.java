package ru.vsu.hb.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.stream.Collectors;

public class GlobalStatistics {

    private Map<String, BigDecimal> currMonthExpenses;
    private Map<String, BigDecimal> prevMonthExpenses;

    public GlobalStatistics() {
    }

    public GlobalStatistics(Map<String, BigDecimal> currMonthExpenses, Map<String, BigDecimal> prevMonthExpenses) {
        this.currMonthExpenses = currMonthExpenses;
        this.prevMonthExpenses = prevMonthExpenses;
    }

    @JsonGetter("currMonthSumOut")
    public BigDecimal getCurrMonthSumOut() {
        return currMonthExpenses.values().parallelStream()
                .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
    }

    @JsonGetter("prevMonthSumOut")
    public BigDecimal getPrevMonthSumOut() {
        return prevMonthExpenses.values().parallelStream()
                .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
    }

    @JsonGetter("currMonthPercents")
    public Map<String, BigDecimal> getCurrMothPercents() {
        var sum = getCurrMonthSumOut();
        if (sum.compareTo(BigDecimal.ZERO) == 0) {
            return Map.of();
        }
        return currMonthExpenses.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()
                        .divide(sum, 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))));
    }

    @JsonGetter("prevMonthPercents")
    public Map<String, BigDecimal> getPrevMothPercents() {
        var sum = getPrevMonthSumOut();
        if (sum.compareTo(BigDecimal.ZERO) == 0) {
            return Map.of();
        }
        return prevMonthExpenses.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()
                        .divide(sum, 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))));
    }

    public Map<String, BigDecimal> getCurrMonthExpenses() {
        return currMonthExpenses;
    }

    public void setCurrMonthExpenses(Map<String, BigDecimal> currMonthExpenses) {
        this.currMonthExpenses = currMonthExpenses;
    }

    public Map<String, BigDecimal> getPrevMonthExpenses() {
        return prevMonthExpenses;
    }

    public void setPrevMonthExpenses(Map<String, BigDecimal> prevMonthExpenses) {
        this.prevMonthExpenses = prevMonthExpenses;
    }
}
