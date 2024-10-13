package com.nhnacademy.minidooray8task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.Tag;
import com.nhnacademy.minidooray8task.dto.TagResponse;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray8task.exception.TagAlreadyException;
import com.nhnacademy.minidooray8task.exception.TagNotFoundException;
import com.nhnacademy.minidooray8task.repository.ProjectRepository;
import com.nhnacademy.minidooray8task.repository.TagRepository;
import com.nhnacademy.minidooray8task.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TagServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByProjectId_Success() {
        // given
        Long projectId = 1L;

        // Project 모킹 (생성자와 set을 사용할 수 없으므로 mock을 사용)
        Project project = mock(Project.class);

        // 프로젝트가 존재하는 것으로 설정
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // Tag 리스트 모킹
        List<Tag> tags = new ArrayList<>();
        Tag tag1 = mock(Tag.class);
        Tag tag2 = mock(Tag.class);
        tags.add(tag1);
        tags.add(tag2);

        // 태그 목록을 반환하도록 설정
        when(tagRepository.findAllByProjectId(projectId)).thenReturn(tags);

        // when
        List<TagResponse> tagResponses = tagService.findAllByProjectId(projectId);

        // then
        assertThat(tagResponses).hasSize(2);  // 2개의 태그가 반환되는지 확인
        verify(tagRepository, times(1)).findAllByProjectId(projectId);
        verify(projectRepository, times(1)).findById(projectId);  // 프로젝트가 조회되었는지 확인
    }

    @Test
    void save_Success() throws Exception {
        // given
        Long projectId = 1L;
        String name = "Test Tag";
        Long tagId = 1L;  // 기대하는 tag ID 값

        Project project = mock(Project.class);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // 태그 이름 중복 확인 로직 모킹
        when(tagRepository.existsByName(name)).thenReturn(false);

        // Answer를 사용하여 save 호출 시 Tag의 id 값 설정
        when(tagRepository.save(any(Tag.class))).thenAnswer(invocation -> {
            Tag savedTag = invocation.getArgument(0);  // save() 메서드로 전달된 Tag 객체
            // Tag의 id 값을 리플렉션을 사용하여 설정
            Field idField = Tag.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(savedTag, tagId);  // ID 값 설정
            return savedTag;
        });

        // when
        Long savedTagId = tagService.save(projectId, name);

        // then
        assertThat(savedTagId).isEqualTo(tagId);
        verify(projectRepository, times(1)).findById(projectId);
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void save_TagAlreadyExists() {
        // given
        Long projectId = 1L;
        String name = "Test Tag";

        Project project = mock(Project.class);
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        // 태그 이름 중복 예외 발생
        when(tagRepository.existsByName(name)).thenReturn(true);

        // when, then
        assertThrows(TagAlreadyException.class, () -> tagService.save(projectId, name));

        verify(tagRepository, times(1)).existsByName(name);
        verify(tagRepository, times(0)).save(any(Tag.class));  // 태그 저장 시도 안되는지 확인
    }

    @Test
    void updateById_Success() {
        // given
        Long tagId = 1L;
        String newName = "Updated Tag";

        Tag tag = mock(Tag.class);

        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

        // when
        tagService.updateById(tagId, newName);

        // then
        verify(tagRepository, times(1)).findById(tagId);
        verify(tag, times(1)).setName(newName);  // 태그 이름이 정상적으로 변경되었는지 확인
    }

    @Test
    void deleteById_Success() {
        // given
        Long tagId = 1L;
        Tag tag = mock(Tag.class);

        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

        // when
        tagService.deleteById(tagId);

        // then
        verify(tagRepository, times(1)).findById(tagId);
        verify(tagRepository, times(1)).deleteById(tagId);
    }

    @Test
    void deleteById_TagNotFound() {
        // given
        Long tagId = 1L;

        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(TagNotFoundException.class, () -> tagService.deleteById(tagId));

        verify(tagRepository, times(1)).findById(tagId);
        verify(tagRepository, times(0)).deleteById(tagId);  // 삭제 시도 안되는지 확인
    }
}
