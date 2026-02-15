package com.toptier.web.service;

import com.toptier.web.dto.PopupResponse;

import java.time.LocalDate;
import java.util.List;

public interface PopupService {
    List<PopupResponse> getFindAllPopup(LocalDate sDate, LocalDate eDate);
}
