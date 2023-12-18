package com.akshitha.apitask.Models;

import java.util.List;

public class SearchApiResponse {

List<SearchArrayObject>page=null;
    List<SearchArrayObject>results=null;
    List<SearchArrayObject>total_pages=null;
    List<SearchArrayObject>total_results=null;

    public List<SearchArrayObject> getPage() {
        return page;
    }

    public void setPage(List<SearchArrayObject> page) {
        this.page = page;
    }

    public List<SearchArrayObject> getResults() {
        return results;
    }

    public void setResults(List<SearchArrayObject> results) {
        this.results = results;
    }

    public List<SearchArrayObject> getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(List<SearchArrayObject> total_pages) {
        this.total_pages = total_pages;
    }

    public List<SearchArrayObject> getTotal_results() {
        return total_results;
    }

    public void setTotal_results(List<SearchArrayObject> total_results) {
        this.total_results = total_results;
    }
}
