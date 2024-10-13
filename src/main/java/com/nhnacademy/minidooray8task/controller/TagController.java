package com.nhnacademy.minidooray8task.controller;

import com.nhnacademy.minidooray8task.dto.TagRequest;
import com.nhnacademy.minidooray8task.dto.TagResponse;
import com.nhnacademy.minidooray8task.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/tags")
@Slf4j
public class TagController {

    private final TagService tagService;

    @GetMapping
    public List<TagResponse> findAllByProjectId(@PathVariable Long projectId) {
        log.info("projectId : {}", projectId);
        return tagService.findAllByProjectId(projectId);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Long save(@PathVariable Long projectId, @RequestBody TagRequest tagRequest) {
        log.info("projectId : {}, tagRequest : {}", projectId, tagRequest.toString());
        return tagService.save(projectId, tagRequest.name());
    }

    @PutMapping("/{tagId}")
    public void updateById(@PathVariable Long projectId,
                           @PathVariable Long tagId,
                           @RequestBody TagRequest tagRequest) {
        log.info("projectId : {}, tagId : {}, tagRequest : {}", projectId, tagId, tagRequest.toString());
        tagService.updateById(tagId, tagRequest.name());
    }

    @DeleteMapping("/{tagId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long projectId,
                           @PathVariable Long tagId) {
        log.info("projectId : {}, tagId : {}", projectId, tagId);
        tagService.deleteById(tagId);
    }
}
