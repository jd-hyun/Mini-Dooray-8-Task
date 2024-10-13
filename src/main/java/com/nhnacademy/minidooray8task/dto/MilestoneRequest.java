package com.nhnacademy.minidooray8task.dto;

import java.time.LocalDate;

public record MilestoneRequest(String title, LocalDate startDate, LocalDate endDate) {
}
