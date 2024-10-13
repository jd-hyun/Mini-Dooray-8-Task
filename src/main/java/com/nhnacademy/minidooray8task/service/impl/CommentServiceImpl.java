package com.nhnacademy.minidooray8task.service.impl;

import com.nhnacademy.minidooray8task.domain.Comment;
import com.nhnacademy.minidooray8task.domain.Task;
import com.nhnacademy.minidooray8task.dto.CommentResponse;
import com.nhnacademy.minidooray8task.exception.TaskNotFoundException;
import com.nhnacademy.minidooray8task.repository.CommentRepository;
import com.nhnacademy.minidooray8task.repository.TaskRepository;
import com.nhnacademy.minidooray8task.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final TaskRepository taskRepository;

    @Override
    public List<CommentResponse> findAllByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId).stream()
                .map(comment -> new CommentResponse(comment.getId(), comment.getContents(), comment.getCreatedAt(), taskId, comment.getAuthorId()))
                .collect(Collectors.toList());
    }

    @Override
    public Long save(String authorId, Long taskId, String contents) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        Comment comment = Comment.createComment(contents, task, authorId);
        commentRepository.save(comment);

        return comment.getId();
    }
}
