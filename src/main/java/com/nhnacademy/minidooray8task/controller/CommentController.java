package com.nhnacademy.minidooray8task.controller;

import com.nhnacademy.minidooray8task.dto.CommentRequest;
import com.nhnacademy.minidooray8task.dto.CommentResponse;
import com.nhnacademy.minidooray8task.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/tasks/{taskId}/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentResponse> findAllByTaskId(@PathVariable Long projectId, @PathVariable Long taskId) {
        log.info("projectId : {}, taskId : {}", projectId, taskId);
        return commentService.findAllByTaskId(taskId);
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long save(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody CommentRequest commentRequest) {
        log.info("projectId : {}, taskId : {}, commentRequest : {}", projectId, taskId, commentRequest.toString());
        return commentService.save(commentRequest.authorId(), taskId, commentRequest.content());
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long projectId,
                           @PathVariable Long taskId,
                           @PathVariable Long commentId) {
        log.info("projectId : {}, taskId : {}, commentId : {}", projectId, taskId, commentId);
//        commentService.deleteById()
    }
}
