package com.nhnacademy.minidooray8task.dto;

import com.nhnacademy.minidooray8task.domain.State;

public record ProjectUpdateRequest(String title, State state) {
}
