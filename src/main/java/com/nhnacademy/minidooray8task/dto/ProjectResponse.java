package com.nhnacademy.minidooray8task.dto;


import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.State;
import lombok.Data;

@Data
public class ProjectResponse {

    private Long id;
    private String title;
    private State state;
    private Long authorId;

    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.title = project.getTitle();
        this.state = project.getState();
        this.authorId = project.getAuthorId();
    }
}
