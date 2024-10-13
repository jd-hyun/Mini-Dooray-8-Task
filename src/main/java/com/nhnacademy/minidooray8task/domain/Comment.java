package com.nhnacademy.minidooray8task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_contents")
    private String contents;

    @Column(name = "comment_createdAt")
    private ZonedDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    private Long accountId;

    public static Comment createComment(String contents, Task task, Long accountId) {
        Comment comment = new Comment();
        comment.contents = contents;
        comment.createdAt = ZonedDateTime.now();
        comment.task = task;
        comment.accountId = accountId;
        return comment;
    }
}
