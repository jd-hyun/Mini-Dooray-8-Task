package com.nhnacademy.minidooray8task.domain;

import com.nhnacademy.minidooray8task.dto.TaskResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tasks_milestones")
public class TaskMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_milestone_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @Setter
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    public TaskMilestone(Task task, Milestone milestone) {
        this.task = task;
        this.milestone = milestone;
    }
}
