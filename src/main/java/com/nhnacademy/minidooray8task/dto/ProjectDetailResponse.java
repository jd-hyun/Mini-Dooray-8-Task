package com.nhnacademy.minidooray8task.dto;

import com.nhnacademy.minidooray8task.domain.Account;
import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.ProjectAccount;
import com.nhnacademy.minidooray8task.domain.State;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProjectDetailResponse {
    private Long id;
    private String title;
    private State state;
    private Long authorId;
    private List<AccountResponse> members;

    public ProjectDetailResponse(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.state = project.getState();
        this.authorId = project.getAuthorId();
        this.members = project.getProjectAccounts().stream()
                .map(ProjectAccount::getAccount)
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }
}
