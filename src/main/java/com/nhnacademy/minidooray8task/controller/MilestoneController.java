package com.nhnacademy.minidooray8task.controller;

import com.nhnacademy.minidooray8task.dto.MilestoneRequest;
import com.nhnacademy.minidooray8task.dto.MilestoneResponse;
import com.nhnacademy.minidooray8task.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/milestones")
@Slf4j
public class MilestoneController {

    private final MilestoneService milestoneService;

    @GetMapping
    public List<MilestoneResponse> findAllByProjectId(@PathVariable Long projectId) {
        log.info("projectId : {}", projectId);
        return milestoneService.findAllByProjectId(projectId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long save(@PathVariable Long projectId,
                     @RequestBody MilestoneRequest milestoneRequest) {
        log.info("projectId : {}, milestoneRequest : {}", projectId, milestoneRequest.toString());
        return milestoneService.save(projectId, milestoneRequest.title(), milestoneRequest.startDate(), milestoneRequest.endDate());
    }

    @DeleteMapping("/{milestoneId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long projectId,
                           @PathVariable Long milestoneId) {
        log.info("projectId : {}, milestoneId : {}", projectId, milestoneId);
        milestoneService.deleteById(milestoneId);
    }
}
