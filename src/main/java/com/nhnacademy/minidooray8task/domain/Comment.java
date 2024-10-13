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

    @Column(name = "comment_created_at")
    private ZonedDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    private String authorId;

    private void setTask(Task task) {
        task.getComments().add(this);
        this.task = task;
    }

    public static Comment createComment(String contents, Task task, String authorId) {
        Comment comment = new Comment();
        comment.contents = contents;
        comment.createdAt = ZonedDateTime.now();
        comment.setTask(task);
        comment.authorId = authorId;
        return comment;
    }
}
