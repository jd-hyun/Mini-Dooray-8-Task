package com.nhnacademy.minidooray8task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.nhnacademy.minidooray8task.domain.Milestone;
import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.dto.MilestoneResponse;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray8task.repository.MilestoneRepository;
import com.nhnacademy.minidooray8task.repository.ProjectRepository;
import com.nhnacademy.minidooray8task.service.impl.MilestoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class MilestoneServiceImplTest {

    @Mock
    private MilestoneRepository milestoneRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private MilestoneServiceImpl milestoneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByProjectId_Success() {
        // given
        Long projectId = 1L;
        Project project = mock(Project.class);  // Project 객체 모킹

        Milestone milestone1 = mock(Milestone.class);  // Milestone 객체 모킹
        when(milestone1.getId()).thenReturn(1L);
        when(milestone1.getTitle()).thenReturn("Milestone 1");
        when(milestone1.getStartDate()).thenReturn(LocalDate.now());
        when(milestone1.getEndDate()).thenReturn(LocalDate.now().plusDays(7));

        Milestone milestone2 = mock(Milestone.class);
        when(milestone2.getId()).thenReturn(2L);
        when(milestone2.getTitle()).thenReturn("Milestone 2");
        when(milestone2.getStartDate()).thenReturn(LocalDate.now().minusDays(5));
        when(milestone2.getEndDate()).thenReturn(LocalDate.now().plusDays(2));

        // project와 milestone을 찾는 로직 모킹
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(milestoneRepository.findAllByProjectId(projectId)).thenReturn(Arrays.asList(milestone1, milestone2));

        // when
        List<MilestoneResponse> milestoneResponses = milestoneService.findAllByProjectId(projectId);

        // then
        assertThat(milestoneResponses).hasSize(2);
        assertThat(milestoneResponses.get(0).id()).isEqualTo(1L);
        assertThat(milestoneResponses.get(0).title()).isEqualTo("Milestone 1");
        assertThat(milestoneResponses.get(1).id()).isEqualTo(2L);
        assertThat(milestoneResponses.get(1).title()).isEqualTo("Milestone 2");

        verify(projectRepository, times(1)).findById(projectId);
        verify(milestoneRepository, times(1)).findAllByProjectId(projectId);
    }

    @Test
    void save_Success() throws Exception {
        // given
        Long projectId = 1L;
        Long milestoneId = 1L;  // 기대하는 milestone ID 값
        String title = "Milestone Title";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);

        Project project = mock(Project.class);  // 프로젝트 객체 모킹

        // Project를 찾는 로직 모킹
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Answer를 사용하여 save 호출 시 Milestone의 id 값 설정
        when(milestoneRepository.save(any(Milestone.class))).thenAnswer(invocation -> {
            Milestone savedMilestone = invocation.getArgument(0);  // save() 메서드로 전달된 Milestone 객체
            // id 값을 설정
            Field idField = Milestone.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(savedMilestone, milestoneId);  // ID 값을 수동으로 설정
            return savedMilestone;
        });

        // when
        Long savedMilestoneId = milestoneService.save(projectId, title, startDate, endDate);

        // then
        assertThat(savedMilestoneId).isNotNull();  // ID가 null이 아닌지 확인
        assertThat(savedMilestoneId).isEqualTo(milestoneId);  // 기대한 milestone ID가 반환되는지 확인
        verify(projectRepository, times(1)).findById(projectId);  // project 조회 확인
        verify(milestoneRepository, times(1)).save(any(Milestone.class));  // milestone 저장 확인
    }
}
