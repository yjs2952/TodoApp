package com.kakaoix.todoapp.service;

import com.kakaoix.todoapp.dto.TodoItemDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@ComponentScan
@DataJpaTest
public class TodoItemServiceTest {

    @Autowired
    private TodoItemService todoItemService;

    @Test
    public void todoItem가져오기() {
        TodoItemDto todo = todoItemService.getModifyTodoItem(4L);
        log.info("todo : {}", todo.toString());
    }
}
