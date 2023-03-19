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

    Todo getNewTodoInstanceOfTodoToAdd() {
        return new Todo("3", "C", State.OPEN);
    }

    @BeforeEach
    void init() {
        todoRepo = new TodoRepo();
    }

    @Test
    void add_shouldReturnAddedTodo() {
        // WHEN
        Todo actual = todoRepo.save(todoToAdd);

        // THEN
        Todo expected = getNewTodoInstanceOfTodoToAdd();
        assertEquals(expected, actual);
    }

    @Test
    void getAll_shouldReturnEmptyList_whenRepoIsEmpty() {
        // WHEN
        List<Todo> actual = todoRepo.getAll();

        // THEN
        List<Todo> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    void getAll_shouldReturnListWithOneTodo_whenRepoContainsOneTodo() {
        //GIVEN
        todoRepo.save(todoToAdd);

        // WHEN
        List<Todo> actual = todoRepo.getAll();

        // THEN
        List<Todo> expected = new ArrayList<>(List.of(
                getNewTodoInstanceOfTodoToAdd()
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
        todoRepo.save(todoToAdd);

        // WHEN
        Todo actual = todoRepo.get("3");

        // THEN
        Todo expected = getNewTodoInstanceOfTodoToAdd();
        assertEquals(expected, actual);
    }

    @Test
    void replace_shouldReturnTheNewTodo_whenRepoIsEmpty() {
        Todo newTodo = getNewTodoInstanceOfTodoToAdd();

        Todo actual = todoRepo.save(newTodo);

        assertEquals(getNewTodoInstanceOfTodoToAdd(), actual);
    }

    @Test
    void delete_shouldReturnNull_whenRepoIsEmpty() {
        Todo actual = todoRepo.delete("doesntExist");

        assertNull(actual);
    }

    @Test
    void delete_shouldReturnDeletedItem_whenRepoContainsId() {
        Todo todoToDelete = getNewTodoInstanceOfTodoToAdd();
        todoRepo.save(todoToDelete);

        Todo actual = todoRepo.delete(todoToDelete.id());

        Todo expected = getNewTodoInstanceOfTodoToAdd();
        assertEquals(expected, actual);
    }
}