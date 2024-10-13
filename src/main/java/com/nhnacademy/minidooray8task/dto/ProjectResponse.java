package com.nhnacademy.minidooray8task.dto;


import com.nhnacademy.minidooray8task.domain.State;

public record ProjectResponse(Long id, String title, State state, String authorId) {

}
