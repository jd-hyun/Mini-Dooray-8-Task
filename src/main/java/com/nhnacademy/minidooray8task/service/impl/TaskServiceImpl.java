package com.nhnacademy.minidooray8task.service.impl;

import com.nhnacademy.minidooray8task.domain.Milestone;
import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.Task;
import com.nhnacademy.minidooray8task.dto.CommentResponse;
import com.nhnacademy.minidooray8task.dto.MilestoneResponse;
import com.nhnacademy.minidooray8task.dto.TaskResponse;
import com.nhnacademy.minidooray8task.exception.MilestoneNotFoundException;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray8task.exception.TaskNotFoundException;
import com.nhnacademy.minidooray8task.repository.MilestoneRepository;
import com.nhnacademy.minidooray8task.repository.ProjectRepository;
import com.nhnacademy.minidooray8task.repository.TaskRepository;
import com.nhnacademy.minidooray8task.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private final MilestoneRepository milestoneRepository;

    @Override
    public TaskResponse findByIdAndProjectId(Long id, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        Task task = taskRepository.findByIdAndProjectIdWithCommentsAndTaskMilestoneAndMilestone(id, projectId).orElseThrow(TaskNotFoundException::new);

        List<String> tags = task.getTaskTags().stream()
                .map(taskTag -> taskTag.getTag().getName())
                .collect(Collectors.toList());

        List<CommentResponse> commentResponses = task.getComments().stream()
                .map(comment -> new CommentResponse(comment.getId(), comment.getContents(), comment.getCreatedAt(), id, project.getAuthorId()))
                .collect(Collectors.toList());

        Milestone milestone = task.getTaskMilestone().getMilestone();
        MilestoneResponse milestoneResponse = new MilestoneResponse(milestone.getId(), milestone.getTitle(), milestone.getStartDate(), milestone.getEndDate());

        return new TaskResponse(task.getId(), task.getTitle(), task.getContents(), projectId, tags, commentResponses, milestoneResponse);
    }

    @Override
    @Transactional
    public Long save(String title, String contents, Long projectId, Long milestoneId) {
        Project project = projectRepository.findByIdWithTasks(projectId).orElseThrow(ProjectNotFoundException::new);
        Milestone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new MilestoneNotFoundException("milestone not found : " + milestoneId));
        Task task = Task.createTask(title, contents, project, milestone);
        taskRepository.save(task);
        return task.getId();
    }
}
