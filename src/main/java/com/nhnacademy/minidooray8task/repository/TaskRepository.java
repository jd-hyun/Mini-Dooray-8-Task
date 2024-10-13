package com.nhnacademy.minidooray8task.repository;

import com.nhnacademy.minidooray8task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t " +
            "LEFT JOIN FETCH t.comments c " +
            "WHERE t.id = :id AND t.project.id = :projectId")
    Optional<Task> findByIdAndProjectIdWithComments(@Param("id") Long id, @Param("projectId") Long projectId);
}
