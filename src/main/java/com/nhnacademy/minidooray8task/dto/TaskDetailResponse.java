package com.nhnacademy.minidooray8task.dto;

import com.nhnacademy.minidooray8task.domain.Milestone;
import com.nhnacademy.minidooray8task.domain.Task;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TaskDetailResponse {
    private Long id;
    private String title;
    private String content;
    private List<String> tags;
    private MilestoneResponse milestone;

    public TaskDetailResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.content = task.getContents();
        this.tags = task.getTaskTags().stream()
                .map(taskTag -> taskTag.getTag().getName())
                .collect(Collectors.toList());
        Milestone milestone = task.getTaskMilestone().getMilestone();
        this.milestone = new MilestoneResponse(milestone.getId(), milestone.getTitle(), milestone.getStartDate(), milestone.getEndDate());
    }
}
