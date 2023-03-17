package com.example.backend.service;

import com.example.backend.model.Todo;
import com.example.backend.repo.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepo todoRepo;
    private final IdService idService;

    public TodoService(TodoRepo todoRepo, IdService idService) {
        this.todoRepo = todoRepo;
        this.idService = idService;
    }

    public Todo add(Todo todo) {
        Todo newToDo = new Todo(idService.generateId(), todo.description(), todo.status());
        return todoRepo.add(newToDo);
    }

    public List<Todo> getAll() {
        return todoRepo.getAll();
    }

    public Todo get(String id) {
        return todoRepo.get(id);
    }

    public Todo replace(String id, Todo todo) {
        return todoRepo.replace(id, todo);
    }

    public Todo delete(String id) {
        return todoRepo.delete(id);
    }
}
