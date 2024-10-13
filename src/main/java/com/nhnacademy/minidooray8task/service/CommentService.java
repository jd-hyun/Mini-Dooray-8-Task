package com.nhnacademy.minidooray8task.service;

import com.nhnacademy.minidooray8task.dto.CommentResponse;

import java.util.List;

public interface CommentService {
    List<CommentResponse> findAllByTaskId(Long taskId);

    Long save(String authorId, Long taskId, String contents);

    void deleteById(Long id);
}
