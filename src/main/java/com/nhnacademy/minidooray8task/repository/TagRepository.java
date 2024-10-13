package com.nhnacademy.minidooray8task.repository;

import com.nhnacademy.minidooray8task.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByProjectId(Long projectId);

    boolean existsByName(String name);
}
