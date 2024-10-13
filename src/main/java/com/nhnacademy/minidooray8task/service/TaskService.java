package com.nhnacademy.minidooray8task.service;

import com.nhnacademy.minidooray8task.dto.TaskResponse;

public interface TaskService {
    TaskResponse findByIdAndProjectId(Long id, Long projectId);

    Long save(String title, String contents, Long projectId, Long milestoneId);
}
