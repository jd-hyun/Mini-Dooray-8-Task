package com.nhnacademy.minidooray8task.repository;

import com.nhnacademy.minidooray8task.domain.Project;

import java.util.Optional;

public interface ProjectQuerydslRepository {
    Optional<Project> findByIdWithProjectAccountsAndAccounts(Long projectId);
}
