package com.akshitha.apitask.Listeners;

import com.akshitha.apitask.Models.SearchApiResponse;

public interface OnSearchApiListener {
    void onResponse(SearchApiResponse response);
    void onError(String message);
}
