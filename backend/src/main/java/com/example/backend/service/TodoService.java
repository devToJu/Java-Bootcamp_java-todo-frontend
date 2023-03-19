package com.example.backend.service;

import com.example.backend.model.Todo;
import com.example.backend.repo.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        return todoRepo.save(newToDo);
    }

    public List<Todo> getAll() {
        return todoRepo.getAll();
    }

    public Todo get(String id) {
        Todo todo = todoRepo.get(id);

        if (todo == null) {
            throw new NoSuchElementException("Todo with Id: '" + id + "' not found!");
        }

        return todo;
    }

    public Todo update(Todo todo) {
        return todoRepo.save(todo);
    }

    public Todo delete(String id) {
        Todo deletedTodo = todoRepo.delete(id);

        if (deletedTodo == null) {
            // Wie würde man denn damit umgehen?
            // Wäre das ein 200 OK weil deletedTodo nicht im Repo?
            // Oder ist das dann ein Fehlerstatus?
            throw new NoSuchElementException("Todo with Id: '" + id + "' not found!");
        }

        return deletedTodo;
    }
}
