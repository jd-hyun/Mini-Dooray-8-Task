package com.nhnacademy.minidooray8task.service;

import com.nhnacademy.minidooray8task.domain.State;
import com.nhnacademy.minidooray8task.dto.ProjectDetailResponse;
import com.nhnacademy.minidooray8task.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {
    List<ProjectResponse> findAllByAuthorId(String authorId);

    ProjectDetailResponse findById(Long projectId);

    Long save(String title, String authorId);

    void updateById(Long projectId, String title, State state);

    void deleteById(Long projectId);
}
