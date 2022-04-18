package ru.vsu.hb.dto.request;

import ru.vsu.hb.dto.SortOrder;

public class PageRequest {

    private int limit = 20;
    private int pageNumber = 0;
    private String sortField;
    private SortOrder sortOrder = SortOrder.ASC;

    public PageRequest() {
    }

    public PageRequest(int limit, int pageNumber, String sortField, SortOrder sortOrder) {
        this.limit = limit;
        this.pageNumber = pageNumber;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
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
}
