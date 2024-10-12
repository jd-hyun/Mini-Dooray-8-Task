package com.nhnacademy.minidooray8task.repository;

import com.nhnacademy.minidooray8task.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByProjectAccountsAccountId(Long accountId);

    @Query("SELECT p FROM Project p " +
            "JOIN FETCH p.projectAccounts pa " +
            "JOIN FETCH pa.account " +
            "WHERE p.id = :projectId")
    Optional<Project> findByIdWithProjectAccountsAndAccounts(@Param("projectId") Long projectId);

    @Query("SELECT p FROM Project p " +
            "LEFT JOIN FETCH p.tasks t " +
            "WHERE p.id = :projectId")
    Optional<Project> findByIdWithTasks(@Param("projectId") Long projectId);

    boolean existsByTitle(String title);
}
