package com.nhnacademy.minidooray8task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "projects_accounts")
public class ProjectAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_account_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @Setter
    private Project project;

    private Long accountId;

    public static ProjectAccount createProjectAccount(Long accountId) {
        ProjectAccount projectAccount = new ProjectAccount();
        projectAccount.role = Role.ADMIN;
        projectAccount.accountId = accountId;

        return projectAccount;
    }
}
