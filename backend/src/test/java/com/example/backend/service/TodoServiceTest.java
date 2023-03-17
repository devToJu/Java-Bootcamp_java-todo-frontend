package com.example.backend.service;

import com.example.backend.model.State;
import com.example.backend.model.Todo;
import com.example.backend.repo.TodoRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    TodoService todoService;
    IdService idService = mock(IdService.class);
    TodoRepo todoRepo = mock(TodoRepo.class);


    Todo todoToAdd = new Todo("3", "C", State.OPEN);

    Todo getNewTodoInstanceOfTodoToAdd() {
        return new Todo("3", "C", State.OPEN);
    }

    @BeforeEach
    void init() {
        todoService = new TodoService(todoRepo, idService);
    }

    @Test
    void add_shouldReturnAddedTodo() {
        when(idService.generateId())
                .thenReturn(todoToAdd.id());

        when(todoRepo.add(todoToAdd))
                .thenReturn(todoToAdd);

        Todo actual = todoService.add(todoToAdd);

        verify(idService).generateId();
        verify(todoRepo).add(todoToAdd);

        Todo expected = getNewTodoInstanceOfTodoToAdd();
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
        assertEquals(0, actual.size());
    }

    @Test
    void getAll_shouldReturnListWithOneTodo_whenRepoContainsOneTodo() {
        ArrayList<Todo> todos = new ArrayList<>(List.of(todoToAdd));
        when(todoRepo.getAll())
                .thenReturn(todos);

        List<Todo> actual = todoService.getAll();

        verify(todoRepo).getAll();

        ArrayList<Todo> expected = new ArrayList<>(List.of(todoToAdd));
        assertEquals(expected, actual);
        assertEquals(1, actual.size());
    }

    @Test
    void get_shouldReturnNull_whenIdIsEmpty() {
        when(todoRepo.get("1"))
                .thenReturn(null);

        Todo actual = todoService.get("1");

        verify(todoRepo).get("1");
        assertNull(actual);
    }

    @Test
    void get_shouldReturnTodoWithGivenId_whenRepoContainsTodoWithGivenId() {
        when(todoRepo.get(todoToAdd.id()))
                .thenReturn(todoToAdd);

        Todo actual = todoService.get(todoToAdd.id());

        verify(todoRepo).get(todoToAdd.id());

        Todo expected = getNewTodoInstanceOfTodoToAdd();
        assertEquals(expected, actual);
    }

    @Test
    void replace_shouldReturnNewTodo() {
        when(todoRepo.replace(todoToAdd.id(), todoToAdd ))
                .thenReturn(todoToAdd);

        Todo actual = todoService.replace(todoToAdd.id(), todoToAdd);

        verify(todoRepo).replace(todoToAdd.id(), todoToAdd);

        Todo expected = getNewTodoInstanceOfTodoToAdd();
        assertEquals(actual, expected);
    }

    @Test
    void delete_shouldReturnNull_whenRepoIsEmpty() {
        String id = "idNotExist";
        when(todoRepo.delete(id))
                .thenReturn(null);

        Todo actual = todoService.delete(id);

        verify(todoRepo).delete(id);
        assertNull(actual);
    }

    @Test
    void delete_shouldReturnTodoWithGivenId_whenRepoContainsId() {
        String id = todoToAdd.id();
        when(todoRepo.delete(id))
                .thenReturn(todoToAdd);

        Todo actual = todoService.delete(id);

        verify(todoRepo).delete(id);
        Todo expected = getNewTodoInstanceOfTodoToAdd();
        assertEquals(expected, actual);
    }
}