package com.nhnacademy.minidooray8task.repository;

import com.nhnacademy.minidooray8task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByIdAndProjectId(Long id, Long projectId);
}
