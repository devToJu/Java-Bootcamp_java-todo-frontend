package com.example.backend.controller;

import com.example.backend.model.Todo;
import com.example.backend.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;


    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAll() {
        return todoService.getAll();
    }

    @GetMapping("/{id}")
    public Todo getById(@PathVariable String id) {
        return todoService.get(id);
    }

    @PostMapping
    public Todo add(@RequestBody Todo todo) {
        return todoService.add(todo);
    }

    @PutMapping("/{id}")
    public Todo update(@PathVariable String id, @RequestBody Todo todo) {
        if (!id.equals(todo.id())) {
            throw new IllegalArgumentException("Path-Id " + id + " doesn't match with Body-Id " + todo.id());
        }
        return todoService.update(todo);
    }

    @DeleteMapping("/{id}")
    public Todo delete(@PathVariable String id) {
        return todoService.delete(id);
    }

}
