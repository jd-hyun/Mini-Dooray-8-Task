package com.nhnacademy.minidooray8task.repository;

import com.nhnacademy.minidooray8task.domain.Comment;
import com.nhnacademy.minidooray8task.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT p FROM Project p " +
            "JOIN FETCH p.projectAccounts pa " +
            "WHERE p.id = :projectId")
    Optional<Project> findByIdWithProjectAccountsAndAccounts(@Param("projectId") Long projectId);

    @Query("SELECT c FROM Comment c " +
            "JOIN FETCH c.task t " +
            "WHERE t.id = :taskId")
    List<Comment> findAllByTaskId(@Param("taskId") Long taskId);
}
