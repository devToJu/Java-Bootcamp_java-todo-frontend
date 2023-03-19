package com.example.backend.service;

import com.example.backend.model.State;
import com.example.backend.model.Todo;
import com.example.backend.repo.TodoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    TodoService todoService;
    IdService idService = mock(IdService.class);
    TodoRepo todoRepo = mock(TodoRepo.class);


    Todo todoA = new Todo("1", "a", State.OPEN);

    Todo getNewInstanceOfTodoA() {
        return new Todo(todoA.id(), todoA.description(), todoA.status());
    }

    @BeforeEach
    void init() {
        todoService = new TodoService(todoRepo, idService);
    }

    @Test
    void save_shouldReturnSavedTodo() {
        when(idService.generateId())
                .thenReturn(todoA.id());

        when(todoRepo.save(todoA))
                .thenReturn(todoA);

        Todo actual = todoService.add(todoA);

        verify(idService).generateId();
        verify(todoRepo).save(todoA);

        Todo expected = getNewInstanceOfTodoA();
        assertEquals(expected, actual);
    }

    @Test
    void getAll_shouldReturnEmptyList_whenRepoIsEmpty() {
        ArrayList<Todo> todos = new ArrayList<>();

        when(todoRepo.getAll())
                .thenReturn(todos);

        List<Todo> actual = todoService.getAll();

        verify(todoRepo).getAll();

        ArrayList<Todo> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void getAll_shouldReturnListWithOneTodo_whenRepoContainsOneTodo() {
        ArrayList<Todo> todos = new ArrayList<>(List.of(todoA));

        when(todoRepo.getAll())
                .thenReturn(todos);

        List<Todo> actual = todoService.getAll();

        verify(todoRepo).getAll();

        ArrayList<Todo> expected = new ArrayList<>(List.of(todoA));
        assertEquals(expected, actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void get_shouldThrowNoSuchElementException_whenRepoIsEmpty() {
        when(todoRepo.get("1"))
                .thenReturn(null);

        try {
            todoService.get("1");
            fail();
        } catch (Exception NoSuchElementException) {
            // Test OK
        }
        verify(todoRepo).get("1");
    }

    @Test
    void get_shouldReturnTodoWithGivenId_whenRepoContainsId() {
        when(todoRepo.get(todoA.id()))
                .thenReturn(todoA);

        Todo actual = todoService.get(todoA.id());

        verify(todoRepo).get(todoA.id());

        Todo expected = getNewInstanceOfTodoA();
        assertEquals(expected, actual);
    }

    @Test
    void delete_shouldThrowNoSuchElementException_whenRepoIsEmpty() {
        String id = "idNotExist";
        when(todoRepo.delete(id))
                .thenReturn(null);

        try {
            todoService.delete(id);
            fail();
        }
        catch (NoSuchElementException e) {
            // Test OK
        }

        verify(todoRepo).delete(id);
    }

    @Test
    void delete_shouldReturnTodoWithGivenId_whenRepoContainsId() {
        String id = todoA.id();
        when(todoRepo.delete(id))
                .thenReturn(todoA);

        Todo actual = todoService.delete(id);

        verify(todoRepo).delete(id);
        Todo expected = getNewInstanceOfTodoA();
        assertEquals(expected, actual);
    }
}