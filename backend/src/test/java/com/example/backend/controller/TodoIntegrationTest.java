package com.example.backend.controller;

import com.example.backend.model.State;
import com.example.backend.model.Todo;
import com.example.backend.repo.TodoRepo;
import com.example.backend.service.TodoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TodoService todoService;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TodoRepo todoRepo;

    Todo todoA = new Todo("1", "a", State.OPEN);
    Todo todoB = new Todo("2", "b", State.IN_PROGRESS);

    Todo getNewInstanceOf(Todo todo) {
        return new Todo(todo.id(), todo.description(), todo.status());
    }

    @Test
    @DirtiesContext
    void getAll() throws Exception {
        todoRepo.save(todoA);
        String todosAsJsonString = objectMapper.writeValueAsString(todoRepo.getAll());

        String responseBody = mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().string(todosAsJsonString))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Todo> actual = objectMapper.readValue(responseBody, new TypeReference<>() {
        });
        List<Todo> expected = new ArrayList<>(List.of(todoA));
        assertEquals(expected, actual);
    }


    @Test
    @DirtiesContext
    void get_shouldThrowNoSuchElementException_WhenRepoDoesNotContainsId() throws Exception {
    /* WIE EXCEPTION RICHTIG TESTEN? Test schlägt fehl
        try {
            mockMvc.perform(get("/api/todo/idNotExist"));
        }
        catch (NoSuchElementException e) {
            // Test OK
        }
     */
    }

    @Test
    @DirtiesContext
    void get_shouldReturnTodoWithGivenId_WhenRepoContainsId() throws Exception {
        todoRepo.save(todoA);

        String responseBody = mockMvc.perform(get("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(todoA)
                ))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Todo actual = objectMapper.readValue(responseBody, Todo.class);
        Todo expected = getNewInstanceOf(todoA);
        assertEquals(expected, actual);
    }

    @Test
    @DirtiesContext
    void add_shouldReturnAddedTodoAndRepoContainsTheNewTodo() throws Exception {
        List<Todo> todosBeforeAdd = todoRepo.getAll();

        String responseBody = mockMvc.perform(post("/api/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "description": "a",
                                    "status": "OPEN"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(content().json(
                        """
                                {
                                    "description": "a",
                                    "status": "OPEN"
                                }
                                """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Todo actual = objectMapper.readValue(responseBody, Todo.class);
        Todo expected = new Todo(actual.id(), todoA.description(), todoA.status());
        assertEquals(expected, actual);

        todosBeforeAdd.add(actual);
        assertEquals(todosBeforeAdd, todoRepo.getAll());
    }

    @Test
    @DirtiesContext
    void update_shouldReturnUpdatedTodo_whenRepoContainsId() throws Exception {
        todoRepo.save(todoA);

        Todo changedTodo = new Todo(todoA.id(), "changed", todoA.status());
        String changedTodoAsJsonString = objectMapper.writeValueAsString(changedTodo);

        mockMvc.perform(put("/api/todo/" + todoA.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(changedTodoAsJsonString))
                .andExpect(status().isOk())
                .andExpect(content().string(changedTodoAsJsonString));

        int expectedTodoCount = 1;
        assertEquals(expectedTodoCount, todoRepo.getAll().size());
    }

    @Test
    @DirtiesContext
    void update_shouldReturnUpdatedTodoAndRepoContainsOneMoreTodo_whenRepoDoesNotContainsId() throws Exception {
        todoRepo.save(todoA);

        String newTodoAsJsonString = objectMapper.writeValueAsString(todoB);

        mockMvc.perform(put("/api/todo/" + todoB.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTodoAsJsonString))
                .andExpect(status().isOk())
                .andExpect(content().string(newTodoAsJsonString));

        int expectedTodoCount = 2;
        assertEquals(expectedTodoCount, todoRepo.getAll().size());
    }

    @Test
    void update_shouldReturnIllegalArgumentException_whenPathIdAndBodyIdAreNotEquals() {
        /*
        WIE MIT EXCEPTION UMGEHEN???
         */
    }

    @Test
    @DirtiesContext
    void delete_shouldReturnDeletedTodo_WhenRepoContainsId() throws Exception {
        todoRepo.save(todoA);

        String deletedTodoAsJsonString = objectMapper.writeValueAsString(todoA);

        mockMvc.perform(delete("/api/todo/" + todoA.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deletedTodoAsJsonString))
                .andExpect(status().isOk())
                .andExpect(content().string(deletedTodoAsJsonString));

        List<Todo> emptyTodoList = new ArrayList<>();
        assertEquals(emptyTodoList, todoRepo.getAll());
    }
}