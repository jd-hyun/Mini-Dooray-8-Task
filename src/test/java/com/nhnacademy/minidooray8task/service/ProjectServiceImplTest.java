package com.nhnacademy.minidooray8task.service;

import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.ProjectAccount;
import com.nhnacademy.minidooray8task.domain.Role;
import com.nhnacademy.minidooray8task.domain.State;
import com.nhnacademy.minidooray8task.dto.ProjectDetailResponse;
import com.nhnacademy.minidooray8task.dto.ProjectResponse;
import com.nhnacademy.minidooray8task.exception.ProjectAlreadyExistsException;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray8task.repository.ProjectRepository;
import com.nhnacademy.minidooray8task.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByAuthorId_Success() {
        // given
        String authorId = "author123";
        ProjectAccount projectAccount = ProjectAccount.createProjectAccount(authorId);
        Project project = Project.createProject("Test Project", authorId, projectAccount);
        when(projectRepository.findAllByProjectAccountsAuthorId(authorId)).thenReturn(List.of(project));

        // when
        List<ProjectResponse> projects = projectService.findAllByAuthorId(authorId);

        // then
        assertThat(projects).hasSize(1);
        assertThat(projects.get(0).id()).isEqualTo(project.getId());
        assertThat(projects.get(0).title()).isEqualTo("Test Project");
    }

    @Test
    void findById_Success() {
        // given
        Long projectId = 1L;
        String authorId = "author123";
        ProjectAccount projectAccount = ProjectAccount.createProjectAccount(authorId);
        Project project = Project.createProject("Test Project", authorId, projectAccount);
        when(projectRepository.findByIdWithProjectAccountsAndAccounts(projectId)).thenReturn(Optional.of(project));

        // when
        ProjectDetailResponse projectDetail = projectService.findById(projectId);

        // then
        assertThat(projectDetail.getId()).isEqualTo(project.getId());
        assertThat(projectDetail.getTitle()).isEqualTo("Test Project");
    }

    @Test
    void findById_Throws_ProjectNotFoundException() {
        // given
        Long projectId = 1L;
        when(projectRepository.findByIdWithProjectAccountsAndAccounts(projectId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ProjectNotFoundException.class, () -> projectService.findById(projectId));
    }

    @Test
    void save_Success() {
        // given
        String title = "New Project";
        String authorId = "author123";
        when(projectRepository.existsByTitle(title)).thenReturn(false);

        ProjectAccount projectAccount = ProjectAccount.createProjectAccount(authorId);
        Project project = Project.createProject(title, authorId, projectAccount);
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        // when
        Long savedProjectId = projectService.save(title, authorId);

        // then
        assertThat(savedProjectId).isEqualTo(project.getId());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    void save_Throws_ProjectAlreadyExistsException() {
        // given
        String title = "Existing Project";
        String authorId = "author123";
        when(projectRepository.existsByTitle(title)).thenReturn(true);

        // when, then
        assertThrows(ProjectAlreadyExistsException.class, () -> projectService.save(title, authorId));
    }

    @Test
    void updateById_Success() {
        // given
        Long projectId = 1L;
        String newTitle = "Updated Project";
        State newState = State.SLEEP;
        String authorId = "author123";
        ProjectAccount projectAccount = ProjectAccount.createProjectAccount(authorId);
        Project project = Project.createProject("Old Project", authorId, projectAccount);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when
        projectService.updateById(projectId, newTitle, newState);

        // then
        assertThat(project.getTitle()).isEqualTo(newTitle);
        assertThat(project.getState()).isEqualTo(newState);
        verify(projectRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateById_Throws_ProjectNotFoundException() {
        // given
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ProjectNotFoundException.class, () -> projectService.updateById(projectId, "New Title", State.SLEEP));
    }

    @Test
    void deleteById_Success() {
        // given
        Long projectId = 1L;
        String authorId = "author123";
        ProjectAccount projectAccount = ProjectAccount.createProjectAccount(authorId);
        Project project = Project.createProject("Test Project", authorId, projectAccount);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // when
        projectService.deleteById(projectId);

        // then
        verify(projectRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteById_Throws_ProjectNotFoundException() {
        // given
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ProjectNotFoundException.class, () -> projectService.deleteById(projectId));
    }


}
