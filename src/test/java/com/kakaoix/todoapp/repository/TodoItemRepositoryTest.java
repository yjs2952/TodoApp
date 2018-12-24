package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoItemRepositoryTest {

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Test
    public void todoItem_참조추가() {
        TodoItem todoItem = todoItemRepository.getOne(1L);
        log.info(todoItem.getContent());
        List<TodoItem> todoItems = new ArrayList<>();
        todoItems.add(todoItemRepository.getOne(2L));
        todoItems.add(todoItemRepository.getOne(3L));
        todoItem.setReferenceItems(todoItems);
        todoItemRepository.saveAndFlush(todoItem);
        Assert.assertEquals(todoItemRepository.getOne(1L).getReferenceItems().size(), todoItem.getReferenceItems().size());

    }

}
