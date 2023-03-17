package com.example.backend.repo;

import com.example.backend.model.State;
import com.example.backend.model.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoRepoTest {

    TodoRepo todoRepo;
    Todo todoToAdd = new Todo("3", "C", State.OPEN);

    Todo createNewTodoEqualsTodoToAdd() {
        return new Todo("3", "C", State.OPEN);
    }

    @BeforeEach
    void init() {
        todoRepo = new TodoRepo();
    }

    @Test
    void add_shouldReturnAddedTodo() {
        // WHEN
        Todo actual = todoRepo.add(todoToAdd);

        // THEN
        Todo expected = createNewTodoEqualsTodoToAdd();
        assertEquals(expected, actual);
    }

    @Test
    void getAll_shouldReturnEmptyList() {
        // WHEN
        List<Todo> actual = todoRepo.getAll();

        // THEN
        List<Todo> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void getAll_shouldReturnListWithOneTodo_whenRepoContainsOneTodo() {
        //GIVEN
        todoRepo.add(todoToAdd);

        // WHEN
        List<Todo> actual = todoRepo.getAll();

        // THEN
        List<Todo> expected = new ArrayList<>(List.of(
                createNewTodoEqualsTodoToAdd()
        ));
        assertEquals(expected, actual);
    }

    @Test
    void get_shouldReturnNull_whenRepoIsEmpty() {
        // WHEN
        Todo actual = todoRepo.get("3");

        // THEN
        assertNull(actual);
    }
    @Test
    void get_shouldReturnTodoWithId_whenRepoContainsGivenId() {
        // GIVEN
        todoRepo.add(todoToAdd);

        // WHEN
        Todo actual = todoRepo.get("3");

        // THEN
        Todo expected = createNewTodoEqualsTodoToAdd();
        assertEquals(expected, actual);
    }
}