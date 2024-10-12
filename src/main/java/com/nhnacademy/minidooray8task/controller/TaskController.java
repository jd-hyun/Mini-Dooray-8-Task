package com.nhnacademy.minidooray8task.controller;

import com.nhnacademy.minidooray8task.dto.TaskRequest;
import com.nhnacademy.minidooray8task.dto.TaskResponse;
import com.nhnacademy.minidooray8task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/tasks")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public TaskResponse findByIdAndProjectId(@PathVariable Long projectId, @PathVariable Long taskId) {
        log.info("projectId : {}, taskId : {}", projectId, taskId);
        return taskService.findByIdAndProjectId(taskId, projectId);
    }

    @PostMapping
    public Long save(@PathVariable Long projectId, @RequestBody TaskRequest taskRequest) {
        log.info("projectId : {}, taskRequest : {}", projectId, taskRequest.toString());
        return taskService.save(taskRequest.title(), taskRequest.contents(), projectId);

    }
}
