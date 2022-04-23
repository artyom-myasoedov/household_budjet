package ru.vsu.hb.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.math.BigDecimal;
import java.util.Map;

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
