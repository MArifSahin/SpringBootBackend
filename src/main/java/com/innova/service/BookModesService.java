package com.innova.service;

import com.innova.dto.request.MoodsForm;
import com.innova.dto.response.DashboardBookResponse;

import java.util.Map;

public interface BookModesService {
    public Map<String, DashboardBookResponse> getBookOfMood(MoodsForm moodsForm);
}
