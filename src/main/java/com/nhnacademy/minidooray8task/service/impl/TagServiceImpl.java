package com.nhnacademy.minidooray8task.service.impl;

import com.nhnacademy.minidooray8task.domain.Project;
import com.nhnacademy.minidooray8task.domain.Tag;
import com.nhnacademy.minidooray8task.dto.TagResponse;
import com.nhnacademy.minidooray8task.exception.ProjectNotFoundException;
import com.nhnacademy.minidooray8task.exception.TagAlreadyException;
import com.nhnacademy.minidooray8task.exception.TagNotFoundException;
import com.nhnacademy.minidooray8task.repository.ProjectRepository;
import com.nhnacademy.minidooray8task.repository.TagRepository;
import com.nhnacademy.minidooray8task.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;

    @Override
    public List<TagResponse> findAllByProjectId(Long projectId) {
        projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
        return tagRepository.findAllByProjectId(projectId).stream()
                .map(tag -> new TagResponse(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Long save(Long projectId, String name) {
        Project project = projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);

        if (tagRepository.existsByName(name)) {
            throw new TagAlreadyException("태그 이름 중복 : " + name);
        }

        Tag tag = Tag.createTag(name, project);
        tagRepository.save(tag);

        return tag.getId();
    }

    @Override
    public void updateById(Long id, String name) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("tag not found : " + id));

        tag.setName(name);
    }

    @Override
    public void deleteById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException("tag not found : " + id));

        tagRepository.deleteById(id);
    }
}
