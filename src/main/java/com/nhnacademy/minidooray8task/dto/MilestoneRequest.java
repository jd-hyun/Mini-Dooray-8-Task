package com.nhnacademy.minidooray8task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MilestoneRequest(String title, LocalDate startDate, LocalDate endDate) {
}
