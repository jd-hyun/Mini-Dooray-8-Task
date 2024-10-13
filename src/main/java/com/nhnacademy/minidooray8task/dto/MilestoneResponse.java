package com.nhnacademy.minidooray8task.dto;

import java.time.LocalDate;

public record MilestoneResponse(Long id, String title, LocalDate startDate, LocalDate endDate) {
}
