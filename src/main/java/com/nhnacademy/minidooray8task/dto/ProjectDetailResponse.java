package com.nhnacademy.minidooray8task.dto;

import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.State;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectDetailResponse {
    private Long id;
    private String title;
    private State state;
    private Long authorId;
    private List<AccountResponse> members;
    private List<TaskDetailResponse> tasks;

    public ProjectDetailResponse(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.state = project.getState();
        this.authorId = project.getAuthorId();
        this.members = project.getProjectAccounts().stream()
                .map(projectAccount -> new AccountResponse(projectAccount.getAuthorId()))
                .collect(Collectors.toList());
        this.tasks = project.getTasks().stream()
                .map(TaskDetailResponse::new)
                .collect(Collectors.toList());
    }
}
