package com.nhnacademy.minidooray8task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "milestones")
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Long id;

    @Column(name = "milestone_name")
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public static Milestone createMilestone(String title, LocalDate startDate, LocalDate endDate, Project project) {
        Milestone milestone = new Milestone();
        milestone.title = title;
        milestone.startDate = startDate;
        milestone.endDate = endDate;
        milestone.project = project;
        return milestone;
    }
}
