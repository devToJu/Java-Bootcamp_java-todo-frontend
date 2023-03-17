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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    Todo newTodo = new Todo("1", "a", State.OPEN);

    @Test
    @DirtiesContext
    void getAll() throws Exception {
        todoRepo.add(newTodo);

        String responseBody = mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                    [
                                        {
                                            "id": "1",
                                            "description": "a",
                                            "status": "OPEN"
                                        }
                                    ]
                                """
                ))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Todo> actual = objectMapper.readValue(responseBody, new TypeReference<>() {});
        List<Todo> expected = new ArrayList<>(List.of(newTodo));
        assertEquals(expected, actual);
    }

    @Test
    void get_() {
    }

    @Test
    void add() {
    }
}