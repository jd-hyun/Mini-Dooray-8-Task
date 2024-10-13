package com.nhnacademy.minidooray8task.service;

import com.nhnacademy.minidooray8task.domain.Comment;
import com.nhnacademy.minidooray8task.domain.Task;
import com.nhnacademy.minidooray8task.dto.CommentResponse;
import com.nhnacademy.minidooray8task.exception.CommentNotFoundException;
import com.nhnacademy.minidooray8task.exception.TaskNotFoundException;
import com.nhnacademy.minidooray8task.repository.CommentRepository;
import com.nhnacademy.minidooray8task.repository.TaskRepository;
import com.nhnacademy.minidooray8task.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByTaskId_Success() {
        // given
        Long taskId = 1L;
        List<Comment> commentList = new ArrayList<>();
        Comment comment1 = mock(Comment.class);
        Comment comment2 = mock(Comment.class);
        commentList.add(comment1);
        commentList.add(comment2);

        when(commentRepository.findAllByTaskId(taskId)).thenReturn(commentList);

        // when
        List<CommentResponse> commentResponses = commentService.findAllByTaskId(taskId);

        // then
        assertThat(commentResponses).hasSize(2);  // 2개의 댓글이 반환되는지 확인
        verify(commentRepository, times(1)).findAllByTaskId(taskId);
    }

    @Test
    void save_Success() throws Exception {
        // given
        Long taskId = 1L;
        String authorId = "author123";
        String contents = "Test comment";
        Long commentId = 1L;  // 기대하는 comment ID 값

        Task task = mock(Task.class);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Answer를 사용하여 save 호출 시 Comment의 id 값 설정
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment savedComment = invocation.getArgument(0);  // save() 메서드로 전달된 Comment 객체
            // Comment의 id 값을 리플렉션을 사용하여 설정
            Field idField = Comment.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(savedComment, commentId);  // ID 값 설정
            return savedComment;
        });

        // when
        Long savedCommentId = commentService.save(authorId, taskId, contents);

        // then
        assertThat(savedCommentId).isEqualTo(commentId);  // 기대한 ID 값과 일치하는지 확인
        verify(taskRepository, times(1)).findById(taskId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void deleteById_Success() {
        // given
        Long commentId = 1L;
        Comment comment = mock(Comment.class);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        // when
        commentService.deleteById(commentId);

        // then
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

}

