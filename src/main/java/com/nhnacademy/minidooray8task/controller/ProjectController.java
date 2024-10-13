package com.nhnacademy.minidooray8task.controller;

import com.nhnacademy.minidooray8task.dto.ProjectDetailResponse;
import com.nhnacademy.minidooray8task.dto.ProjectSaveRequest;
import com.nhnacademy.minidooray8task.dto.ProjectResponse;
import com.nhnacademy.minidooray8task.dto.ProjectUpdateRequest;
import com.nhnacademy.minidooray8task.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;


    @GetMapping
    public List<ProjectResponse> findAllByAccountId(@RequestParam String memberId) {
        log.info("memberId : {}", memberId);
        return projectService.findAllByAuthorId(memberId);
    }

    @GetMapping("/{projectId}")
    public ProjectDetailResponse findById(@PathVariable Long projectId) {
        log.info("projectId : {}", projectId);
        return projectService.findById(projectId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long save(@RequestBody ProjectSaveRequest projectSaveRequest) {
        log.info("projectRequest : {}", projectSaveRequest);
        return projectService.save(projectSaveRequest.title(), projectSaveRequest.authorId());
    }

    @PutMapping("/{projectId}")
    public void updateById(@PathVariable Long projectId, @RequestBody ProjectUpdateRequest projectUpdateRequest) {
        log.info("projectId : {}, projectUpdateRequest : {}", projectId, projectUpdateRequest.toString());
        projectService.updateById(projectId, projectUpdateRequest.title(), projectUpdateRequest.state());
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long projectId) {
        log.info("projectId : {}", projectId);
        projectService.deleteById(projectId);
    }
}
