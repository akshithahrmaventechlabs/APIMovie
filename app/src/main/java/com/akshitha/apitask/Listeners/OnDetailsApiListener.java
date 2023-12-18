package com.akshitha.apitask.Listeners;

import com.akshitha.apitask.Models.DetailApiResponse;

public interface OnDetailsApiListener {
    void onResponse(DetailApiResponse response);
    void onError(String message);
}
