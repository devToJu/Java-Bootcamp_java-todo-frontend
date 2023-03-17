package com.example.backend.model;

public record Todo(
        String id,
        String description,
        State status
) {
}
