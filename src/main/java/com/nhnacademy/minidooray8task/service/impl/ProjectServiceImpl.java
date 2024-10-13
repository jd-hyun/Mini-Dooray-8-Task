package com.nhnacademy.minidooray8task.service.impl;

import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.ProjectAccount;
import com.nhnacademy.minidooray8task.domain.State;
import com.nhnacademy.minidooray8task.dto.ProjectDetailResponse;
import com.nhnacademy.minidooray8task.dto.ProjectResponse;
import com.nhnacademy.minidooray8task.exception.ProjectAlreadyExistsException;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray8task.repository.ProjectRepository;
import com.nhnacademy.minidooray8task.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<ProjectResponse> findAllByAccountId(Long accountId) {
        return projectRepository.findAllByProjectAccountsAccountId(accountId).stream()
                .map(ProjectResponse::new)
                .toList();
    }

    @Override
    public ProjectDetailResponse findById(Long projectId) {
//        Project project = projectRepository.findById(productId).orElseThrow(ProjectNotFoundException::new);
        Project project = projectRepository.findByIdWithProjectAccountsAndAccounts(projectId).orElseThrow(ProjectNotFoundException::new);
        return new ProjectDetailResponse(project);
    }

    @Override
    @Transactional
    public Long save(String title, Long authorId) {
        if (projectRepository.existsByTitle(title)) {
            throw new ProjectAlreadyExistsException();
        }

        ProjectAccount projectAccount = ProjectAccount.createProjectAccount(authorId);
        Project project = Project.createProject(title, authorId, projectAccount);
        projectRepository.save(project);
        return project.getId();
    }

    @Override
    @Transactional
    public void updateById(Long projectId, String title, State state) {
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        project.setTitle(title);
        project.setState(state);
    }

    @Override
    @Transactional
    public void deleteById(Long projectId) {
        projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        projectRepository.deleteById(projectId);
    }
}
