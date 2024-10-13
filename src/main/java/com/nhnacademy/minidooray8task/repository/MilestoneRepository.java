package com.nhnacademy.minidooray8task.repository;

import com.nhnacademy.minidooray8task.domain.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MilestoneRepository extends JpaRepository<Milestone, Long> {

    List<Milestone> findAllByProjectId(Long projectId);
}
