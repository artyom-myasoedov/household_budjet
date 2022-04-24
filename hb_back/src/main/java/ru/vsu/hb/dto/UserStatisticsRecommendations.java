package ru.vsu.hb.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserStatisticsRecommendations {

    private Map<String, BigDecimal> currMonthExpenses;
    private Map<String, BigDecimal> prevMonthExpenses;
    private List<String> recommendations;
    private BigDecimal currMonthSumOut;
    private BigDecimal prevMonthSumOut;
    private Map<String, BigDecimal> currMonthPercents;
    private Map<String, BigDecimal> prevMonthPercents;
    private BigDecimal currMonthSumIn;
    private BigDecimal prevMonthSumIn;

    public UserStatisticsRecommendations() {
    }

    public UserStatisticsRecommendations(Map<String, BigDecimal> currMonthExpenses, Map<String, BigDecimal> prevMonthExpenses) {
        this.currMonthExpenses = currMonthExpenses;
        this.prevMonthExpenses = prevMonthExpenses;
    }

    public BigDecimal getCurrMonthSumIn() {
        return currMonthSumIn;
    }

    public void setCurrMonthSumIn(BigDecimal currMonthSumIn) {
        this.currMonthSumIn = currMonthSumIn;
    }

    public BigDecimal getPrevMonthSumIn() {
        return prevMonthSumIn;
    }

    public void setPrevMonthSumIn(BigDecimal prevMonthSumIn) {
        this.prevMonthSumIn = prevMonthSumIn;
    }

    @JsonGetter("currMonthSumOut")
    public BigDecimal getCurrMonthExpensesSum() {
        if (currMonthSumOut == null) {
            currMonthSumOut = currMonthExpenses.values().parallelStream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
        }
        return currMonthSumOut;
    }

    @JsonGetter("prevMonthSumOut")
    public BigDecimal getPrevMonthExpensesSum() {
        if (prevMonthSumOut == null) {
            prevMonthSumOut = prevMonthExpenses.values().parallelStream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
        }
        return prevMonthSumOut;
    }

    @JsonGetter("currMonthPercents")
    public Map<String, BigDecimal> getCurrMothPercents() {
        var sum = getCurrMonthExpensesSum();
        if (currMonthPercents == null) {
            currMonthPercents = currMonthExpenses.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()
                            .divide(sum, 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))));
        }
        return currMonthPercents;
    }

    @JsonGetter("prevMonthPercents")
    public Map<String, BigDecimal> getPrevMothPercents() {
        var sum = getPrevMonthExpensesSum();
        if (prevMonthPercents == null) {
            prevMonthPercents = prevMonthExpenses.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue()
                            .divide(sum, 6, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100))));
        }
        return prevMonthPercents;
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

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }
}
