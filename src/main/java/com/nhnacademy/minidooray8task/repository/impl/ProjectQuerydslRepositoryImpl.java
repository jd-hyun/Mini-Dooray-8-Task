package com.nhnacademy.minidooray8task.repository.impl;

import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.QProject;
import com.nhnacademy.minidooray8task.repository.ProjectQuerydslRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


import java.util.Optional;

import static com.nhnacademy.minidooray8task.domain.QMilestone.milestone;
import static com.nhnacademy.minidooray8task.domain.QProject.project;
import static com.nhnacademy.minidooray8task.domain.QProjectAccount.projectAccount;
import static com.nhnacademy.minidooray8task.domain.QTask.task;
import static com.nhnacademy.minidooray8task.domain.QTaskMilestone.taskMilestone;
import static com.nhnacademy.minidooray8task.domain.QTaskTag.taskTag;
import static com.querydsl.jpa.JPAExpressions.select;

public class ProjectQuerydslRepositoryImpl extends QuerydslRepositorySupport implements ProjectQuerydslRepository {
    public ProjectQuerydslRepositoryImpl() {
        super(Project.class);
    }

    @Override
    public Optional<Project> findByIdWithProjectAccountsAndAccounts(Long projectId) {
        Project project = from(QProject.project)
                .leftJoin(QProject.project.projectAccounts, projectAccount).fetchJoin()
//                .leftJoin(QProject.project.tasks, task).fetchJoin()
//                .leftJoin(task.taskTags, taskTag).fetchJoin()
//                .leftJoin(taskTag.tag).fetchJoin()
//                .leftJoin(task.taskMilestone, taskMilestone).fetchJoin()
//                .leftJoin(taskMilestone.milestone, milestone).fetchJoin()
                .where(QProject.project.id.eq(projectId))
                .distinct()  // 중복을 제거하기 위해 distinct 사용
                .select(QProject.project)
                .fetchOne();

        return Optional.ofNullable(project);
    }
}
