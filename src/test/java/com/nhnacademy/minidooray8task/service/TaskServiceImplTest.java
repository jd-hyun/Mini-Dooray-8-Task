package com.nhnacademy.minidooray8task.service;

import com.nhnacademy.minidooray8task.domain.*;
import com.nhnacademy.minidooray8task.dto.CommentDetailResponse;
import com.nhnacademy.minidooray8task.dto.MilestoneResponse;
import com.nhnacademy.minidooray8task.dto.TaskResponse;
import com.nhnacademy.minidooray8task.exception.MilestoneNotFoundException;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray8task.exception.TaskNotFoundException;
import com.nhnacademy.minidooray8task.repository.MilestoneRepository;
import com.nhnacademy.minidooray8task.repository.ProjectRepository;
import com.nhnacademy.minidooray8task.repository.TaskRepository;
import com.nhnacademy.minidooray8task.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private MilestoneRepository milestoneRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByIdAndProjectId_WithComments_Success() throws Exception {
        Long taskId = 1L;
        Long projectId = 2L;
        String authorId = "author123";

        ProjectAccount projectAccount = ProjectAccount.createProjectAccount(authorId);
        Project project = Project.createProject("Test Project", authorId, projectAccount);

        Milestone milestone = Milestone.createMilestone("Milestone 1", LocalDate.now(), LocalDate.now().plusDays(7), project);
        Task task = Task.createTask("Test Task", "This is a test task", project, milestone);

        // 리플렉션을 사용하여 Task의 id 값을 수동으로 설정
        Field idField = Task.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(task, taskId);  // id 값 설정

        // comments 리스트 초기화
        Field commentsField = Task.class.getDeclaredField("comments");
        commentsField.setAccessible(true);
        commentsField.set(task, new ArrayList<>());  // comments 리스트 초기화

        // Comment.createComment()에서 이미 setTask()로 comments 리스트에 추가되므로 여기서는 추가하지 않음
        Comment.createComment("Test Comment", task, authorId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepository.findByIdAndProjectIdWithCommentsAndTaskMilestoneAndMilestone(taskId, projectId)).thenReturn(Optional.of(task));

        // when
        TaskResponse response = taskService.findByIdAndProjectId(taskId, projectId);

        // then
        assertThat(response.id()).isEqualTo(taskId);
        assertThat(response.title()).isEqualTo("Test Task");
        assertThat(response.content()).isEqualTo("This is a test task");
        assertThat(response.milestone().title()).isEqualTo("Milestone 1");

        // Check comments (size should be 1)
        assertThat(response.comments()).hasSize(1);
        CommentDetailResponse commentResponse = response.comments().getFirst();
        assertThat(commentResponse.content()).isEqualTo("Test Comment");
        assertThat(commentResponse.authorId()).isEqualTo(authorId);

        verify(projectRepository, times(1)).findById(projectId);
        verify(taskRepository, times(1)).findByIdAndProjectIdWithCommentsAndTaskMilestoneAndMilestone(taskId, projectId);
    }

    @Test
    void save_Success() throws Exception {
        // given
        Long projectId = 1L;
        Long milestoneId = 2L;
        Long taskId = 1L;  // 기대하는 Task ID 값
        String title = "New Task";
        String contents = "Task contents";

        Project project = mock(Project.class);
        Milestone milestone = mock(Milestone.class);

        // 리플렉션을 사용하여 Project의 tasks 리스트 초기화
        Field tasksField = Project.class.getDeclaredField("tasks");
        tasksField.setAccessible(true);
        tasksField.set(project, new ArrayList<>());  // tasks 리스트 초기화

        // Task 객체 생성
        Task task = Task.createTask(title, contents, project, milestone);

        // 리플렉션을 사용하여 Task의 id 값을 수동으로 설정
        Field idField = Task.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(task, taskId);  // id 값을 설정

        // comments 리스트 초기화 (리플렉션 사용)
        Field commentsField = Task.class.getDeclaredField("comments");
        commentsField.setAccessible(true);
        commentsField.set(task, new ArrayList<>());  // comments 리스트 초기화

        // project와 milestone을 찾는 로직 모킹
        when(projectRepository.findByIdWithTasks(projectId)).thenReturn(Optional.of(project));
        when(milestoneRepository.findById(milestoneId)).thenReturn(Optional.of(milestone));

        // taskRepository.save()가 호출될 때, id가 설정된 Task 객체를 반환하도록 설정
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            Field savedTaskIdField = Task.class.getDeclaredField("id");
            savedTaskIdField.setAccessible(true);
            savedTaskIdField.set(savedTask, taskId);  // save된 Task 객체에 id 설정
            return savedTask;
        });

        // when
        Long savedTaskId = taskService.save(title, contents, projectId, milestoneId);

        // then
        assertThat(savedTaskId).isEqualTo(taskId);  // Task의 ID가 기대한 값과 같은지 확인
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(projectRepository, times(1)).findByIdWithTasks(projectId);
        verify(milestoneRepository, times(1)).findById(milestoneId);
    }
}
