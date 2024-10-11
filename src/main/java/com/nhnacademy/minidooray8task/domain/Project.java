package com.nhnacademy.minidooray8task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Setter
    private String title;

    private String contents;

    @Enumerated(value = EnumType.STRING)
    @Setter
    private State state;

    private Long authorId;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    List<ProjectAccount> projectAccounts = new ArrayList<>();

    private void addProjectAccount(ProjectAccount projectAccount) {
        projectAccounts.add(projectAccount);
        projectAccount.setProject(this);
    }

    public static Project createProject(String title, Long authorId, ProjectAccount... projectAccounts) {
        Project project = new Project();
        project.title = title;
        project.state = State.ACTIVE;
        project.authorId = authorId;
        for (ProjectAccount projectAccount : projectAccounts) {
            project.addProjectAccount(projectAccount);
        }
        return project;
    }
}
