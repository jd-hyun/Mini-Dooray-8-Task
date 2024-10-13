package com.nhnacademy.minidooray8task.dto;

import java.time.ZonedDateTime;

public record CommentDetailResponse(Long id, String content, ZonedDateTime createdAt, String authorId) {
}
