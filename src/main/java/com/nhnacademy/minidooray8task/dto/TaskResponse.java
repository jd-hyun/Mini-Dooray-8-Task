package com.nhnacademy.minidooray8task.dto;

import java.util.List;

public record TaskResponse(Long id, String title, String content, Long projectId, List<String> tags, List<CommentResponse> comments, MilestoneResponse milestone) {
}
