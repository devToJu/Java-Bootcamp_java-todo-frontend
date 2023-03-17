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
    public Todo get(@PathVariable String id) {
        return todoService.get(id);
    }

    @PostMapping
    public Todo add(@RequestBody Todo todo) {
        return todoService.add(todo);
    }

    @PutMapping("/{id}")
    public Todo replace(@PathVariable String id, @RequestBody Todo todo) {
        return todoService.replace(id, todo);
    }
}
