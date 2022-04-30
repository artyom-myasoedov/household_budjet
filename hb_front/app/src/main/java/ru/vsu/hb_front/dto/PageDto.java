package ru.vsu.hb_front.dto;

import java.util.List;

public class PageDto<T> {

    private List<T> items;
    private int limit;
    private int currPage;
    private int maxPage;
    private boolean hasNext;
    private String sortField;
    private SortOrder sortOrder;

    public PageDto() {
    }

    public PageDto(List<T> items, int limit, int currPage, int maxPage, boolean hasNext, String sortField, SortOrder sortOrder) {
        this.items = items;
        this.limit = limit;
        this.currPage = currPage;
        this.maxPage = maxPage;
        this.hasNext = hasNext;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
