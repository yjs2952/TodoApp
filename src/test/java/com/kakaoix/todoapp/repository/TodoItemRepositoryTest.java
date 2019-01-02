package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.Status;
import com.kakaoix.todoapp.domain.TodoItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoItemRepositoryTest {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Before
    public void init() {
        List<TodoItem> todoItems = new ArrayList<>();
        todoItems.add(todoItemRepository.getOne(2L));
        todoItems.add(todoItemRepository.getOne(3L));

        TodoItem todoItem = todoItemRepository.save(TodoItem.builder()
                .content("테스트")
                .isChecked(1)
                .status(Status.TODO)
                .regDate(LocalDateTime.now())
                .build());
        todoItem.setStatus(Status.REF);
        log.info("id : {}", todoItem.getId());
    }

    @Test
    public void Todo_생성_테스트() {
        List<TodoItem> todoItems = todoItemRepository.findAll();
        for (TodoItem todoItem : todoItems) {
            log.info(todoItem.getRegDate().toString());
        }
    }

    @Test
    public void Todo_삭제_테스트() {
        todoItemRepository.deleteById(6L);
    }

    @Test
    public void Todo_검색_테스트() {
        List<TodoItem> searchTodoList = todoItemRepository.getTodoItemsByKeyword(7L, "");

        log.info("-------------- 조회결과 --------------\n");
        Assert.assertEquals(25, searchTodoList.size());
        for (TodoItem todoItem : searchTodoList) {
            log.info("id : {}", todoItem.getId());
        }
    }
}
