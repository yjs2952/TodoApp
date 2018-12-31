package com.kakaoix.todoapp.rest;

import com.kakaoix.todoapp.controller.TodoRestController;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoRestControllerTest {

    private MockMvc mockMvc;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @MockBean
    TodoRestController todoRestController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoRestController).build();
    }

    @Test
    public void getTodoItem() throws Exception {
        mockMvc.perform(get("/api/todos/{id}", 1))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
