package ru.vsu.hb.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApiModel(description = "Статистика по пользователю и рекомендации")
public class UserStatisticsRecommendations {

    @ApiModelProperty(value = "Отображение названий категорий на расходы по ним в текущем месяце")
    private Map<String, BigDecimal> currMonthExpenses;
    @ApiModelProperty(value = "Отображение названий категорий на расходы по ним в предыдущем месяце")
    private Map<String, BigDecimal> prevMonthExpenses;
    @ApiModelProperty(value = "Список рекомендаций")
    private List<String> recommendations;
    @ApiModelProperty(value = "Сумма расходов в текущем месяце")
    private BigDecimal currMonthSumOut;
    @ApiModelProperty(value = "Сумма расходов в предыдущем месяце")
    private BigDecimal prevMonthSumOut;
    @ApiModelProperty(value = "Отображение названий категорий на их долю от общих расходов в текущем месяце")
    private Map<String, BigDecimal> currMonthPercents;
    @ApiModelProperty(value = "Отображение названий категорий на их долю от общих расходов в предыдущем месяце")
    private Map<String, BigDecimal> prevMonthPercents;
    @ApiModelProperty(value = "Сумма пополнений в текущем месяце")
    private BigDecimal prevMonthSumIn;
    @ApiModelProperty(value = "Сумма пополнений в предыдущем месяце")
    private BigDecimal currMonthSumIn;

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
