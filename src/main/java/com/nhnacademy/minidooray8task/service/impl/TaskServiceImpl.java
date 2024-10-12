package com.nhnacademy.minidooray8task.service.impl;

import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.Task;
import com.nhnacademy.minidooray8task.dto.TaskResponse;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray8task.exception.TaskNotFoundException;
import com.nhnacademy.minidooray8task.repository.ProjectRepository;
import com.nhnacademy.minidooray8task.repository.TaskRepository;
import com.nhnacademy.minidooray8task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    @Override
    public TaskResponse findByIdAndProjectId(Long id, Long projectId) {
        projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        Task task = taskRepository.findByIdAndProjectId(id, projectId).orElseThrow(TaskNotFoundException::new);

        return new TaskResponse(task.getId(), task.getTitle(), task.getContents(), projectId);
    }

    @Override
    @Transactional
    public Long save(String title, String contents, Long projectId) {
        Project project = projectRepository.findByIdWithTasks(projectId).orElseThrow(ProjectNotFoundException::new);
        Task task = Task.createTask(title, contents, project);
        taskRepository.save(task);
        return task.getId();
    }
}
