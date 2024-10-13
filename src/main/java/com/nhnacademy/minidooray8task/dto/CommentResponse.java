package com.nhnacademy.minidooray8task.dto;

import java.time.ZonedDateTime;

public record CommentResponse(Long id, String contents, ZonedDateTime createdAt, Long taskId, Long authorId) {
}
