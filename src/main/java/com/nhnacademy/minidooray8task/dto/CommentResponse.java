package com.nhnacademy.minidooray8task.dto;

import java.time.ZonedDateTime;

public record CommentResponse(Long id, String content, ZonedDateTime createdAt, Long taskId, String authorId) {
}
