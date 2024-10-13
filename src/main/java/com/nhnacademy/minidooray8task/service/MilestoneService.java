package com.nhnacademy.minidooray8task.service;

import com.nhnacademy.minidooray8task.dto.MilestoneResponse;

import java.time.LocalDate;
import java.util.List;

public interface MilestoneService {

    List<MilestoneResponse> findAllByProjectId(Long projectId);

    Long save(Long projectId, String title, LocalDate startDate, LocalDate endDate);
}
