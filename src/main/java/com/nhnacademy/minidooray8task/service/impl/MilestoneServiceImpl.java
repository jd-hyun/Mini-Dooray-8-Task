package com.nhnacademy.minidooray8task.service.impl;

import com.nhnacademy.minidooray8task.domain.Milestone;
import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.dto.MilestoneResponse;
import com.nhnacademy.minidooray8task.exception.MilestoneNotFoundException;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray8task.repository.MilestoneRepository;
import com.nhnacademy.minidooray8task.repository.ProjectRepository;
import com.nhnacademy.minidooray8task.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;

    @Override
    public List<MilestoneResponse> findAllByProjectId(Long projectId) {
        projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
        return milestoneRepository.findAllByProjectId(projectId).stream()
                .map(milestone -> new MilestoneResponse(milestone.getId(), milestone.getTitle(), milestone.getStartDate(), milestone.getEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Long save(Long projectId, String title, LocalDate startDate, LocalDate endDate) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        Milestone milestone = Milestone.createMilestone(title, startDate, endDate, project);
        milestoneRepository.save(milestone);
        return milestone.getId();
    }

    @Override
    public void deleteById(Long id) {
        milestoneRepository.findById(id)
                .orElseThrow(() -> new MilestoneNotFoundException("milestone not found : " + id));
        milestoneRepository.deleteById(id);
    }
}
