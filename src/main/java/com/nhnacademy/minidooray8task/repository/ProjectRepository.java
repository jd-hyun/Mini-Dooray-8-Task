package com.nhnacademy.minidooray8task.repository;

import com.nhnacademy.minidooray8task.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectQuerydslRepository {
    List<Project> findAllByProjectAccountsAuthorId(String authorId);

//    @Query("SELECT p FROM Project p " +
//            "LEFT JOIN FETCH p.projectAccounts pa " +
//            "LEFT JOIN FETCH p.tasks t " +
//            "LEFT JOIN FETCH t.taskTags tt " +
//            "LEFT JOIN FETCH tt.tag " +
//            "LEFT JOIN FETCH t.taskMilestone tm " +
//            "LEFT JOIN FETCH tm.milestone " +
//            "WHERE p.id = :projectId")
//    Optional<Project> findByIdWithProjectAccountsAndAccounts(@Param("projectId") Long projectId);

    @Query("SELECT p FROM Project p " +
            "LEFT JOIN FETCH p.tasks t " +
            "WHERE p.id = :projectId")
    Optional<Project> findByIdWithTasks(@Param("projectId") Long projectId);

    boolean existsByTitle(String title);
}
