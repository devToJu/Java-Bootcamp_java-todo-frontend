package com.example.backend.repo;

import com.example.backend.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepo {
    private final Map<String, Todo> todos;

    public TodoRepo() {
        this.todos = new HashMap<>();
    }

    public Todo add(Todo newToDo) {
        todos.put(newToDo.id(), newToDo);
        return newToDo;
    }

    public List<Todo> getAll() {
        return new ArrayList<>(todos.values());
    }

    public Todo get(String id) {
        return todos.get(id);
    }

    public Todo replace(String id, Todo todo) {
        todos.put(id, todo);
        return todo;
    }

    public Todo delete(String id) {
        return todos.remove(id);
    }
}
