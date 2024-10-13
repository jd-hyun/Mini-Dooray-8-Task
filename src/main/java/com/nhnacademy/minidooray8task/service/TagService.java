package com.nhnacademy.minidooray8task.service;

import com.nhnacademy.minidooray8task.dto.TagResponse;

import java.util.List;

public interface TagService {

    List<TagResponse> findAllByProjectId(Long projectId);

    Long save(Long projectId, String name);

    void updateById(Long id, String name);

    void deleteById(Long id);
}
