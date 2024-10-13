package com.nhnacademy.minidooray8task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskTag> taskTags = new ArrayList<>();

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    private TaskMilestone taskMilestone;

    private void setProject(Project project) {
        this.project = project;
        this.project.tasks.add(this);
    }

    private void setMilestone(Milestone milestone) {
        this.taskMilestone = new TaskMilestone(this, milestone);
    }

    public static Task createTask(String title, String contents, Project project, Milestone milestone) {
        Task task = new Task();
        task.title = title;
        task.contents = contents;
        task.setProject(project);
        task.setMilestone(milestone);
        return task;
    }
}
