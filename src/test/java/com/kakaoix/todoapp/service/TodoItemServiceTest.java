package com.kakaoix.todoapp.service;

import com.kakaoix.todoapp.dto.TodoItemDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
    public void todoItem_가져오기() {
        TodoItemDto todo = todoItemService.getModifyTodoItem(4L);
        log.info("todo : {}", todo.toString());
    }

    @Test
    public void todoItem_수정() throws Exception {
        TodoItemDto todo = todoItemService.getModifyTodoItem(4L);
        todo.setContent("안녕하세요");

        Assert.assertEquals("수정 완료 하였습니다.", todoItemService.modifyTodoItem(4L, todo));
    }

    @Test
    public void todoItem_삭제() throws Exception{
        todoItemService.deleteTodoItem(4L);
    }
}
