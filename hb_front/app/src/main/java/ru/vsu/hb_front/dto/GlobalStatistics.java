package ru.vsu.hb_front.dto;

import android.graphics.Bitmap;

import java.math.BigDecimal;
import java.util.Map;


public class GlobalStatistics {

    private Map<String, BigDecimal> currMonthExpenses;
    private Map<String, BigDecimal> prevMonthExpenses;
    private BigDecimal currMonthSumOut;
    private BigDecimal prevMonthSumOut;
    private Map<String, BigDecimal> currMonthPercents;
    private Map<String, BigDecimal> prevMonthPercents;

    public GlobalStatistics() {
    }

    public GlobalStatistics(Map<String, BigDecimal> currMonthExpenses, Map<String, BigDecimal> prevMonthExpenses) {
        this.currMonthExpenses = currMonthExpenses;
        this.prevMonthExpenses = prevMonthExpenses;
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

    public BigDecimal getCurrMonthSumOut() {
        return currMonthSumOut;
    }

    public void setCurrMonthSumOut(BigDecimal currMonthSumOut) {
        this.currMonthSumOut = currMonthSumOut;
    }

    public BigDecimal getPrevMonthSumOut() {
        return prevMonthSumOut;
    }

    public void setPrevMonthSumOut(BigDecimal prevMonthSumOut) {
        this.prevMonthSumOut = prevMonthSumOut;
    }

    public Map<String, BigDecimal> getCurrMonthPercents() {
        return currMonthPercents;
    }

    public void setCurrMonthPercents(Map<String, BigDecimal> currMonthPercents) {
        this.currMonthPercents = currMonthPercents;
    }

    public Map<String, BigDecimal> getPrevMonthPercents() {
        return prevMonthPercents;
    }

    public void setPrevMonthPercents(Map<String, BigDecimal> prevMonthPercents) {
        this.prevMonthPercents = prevMonthPercents;
    }
}
