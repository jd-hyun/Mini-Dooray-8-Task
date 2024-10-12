package com.nhnacademy.minidooray8task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "task_title")
    private String title;

    @Column(name = "task_contents")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    private void setProject(Project project) {
        this.project = project;
        this.project.tasks.add(this);
    }

    public static Task createTask(String title, String contents, Project project) {
        Task task = new Task();
        task.title = title;
        task.contents = contents;
        task.setProject(project);
        return task;
    }
}
