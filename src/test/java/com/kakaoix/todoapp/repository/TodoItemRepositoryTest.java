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
        //todoItem.setReferenceItems(todoItems);
        log.info("id : {}", todoItem.getId());
    }

    @Test
    public void Todo_생성_테스트(){
        List<TodoItem> todoItems = todoItemRepository.findAll();
        for (TodoItem todoItem : todoItems) {
            log.info(todoItem.getRegDate().toString());
        }
    }

    @Test
    public void 체크한_todoItems_조회(){
        List<Long> ids = Arrays.asList(1L,2L,3L,4L);
        List<TodoItem> checkedTodos = todoItemRepository.getTodoItemsByIdIn(ids);
        Assert.assertSame(4, checkedTodos.size());
    }

    @Test
    public void 참조_todoItems_조회(){
        TodoItem todoItem = todoItemRepository.getOne(4L);
        //List<TodoItem> refTodos = todoItem.getReferenceItems();
        log.info("상태 :  {}", todoItem.getStatus());
        //Assert.assertSame(4, refTodos.size());
    }

    @Test
    public void 참조_todoItems_삭제(){
        TodoItem todoItem = todoItemRepository.getOne(4L);
//        List<TodoItem> refTodos = todoItem.getReferenceItems();
//        refTodos.remove(todoItemRepository.getOne(1L));
//        refTodos.remove(todoItemRepository.getOne(2L));
        //todoItem.setReferenceItems(refTodos);

        //log.info("삭제후 개수 : {}", todoItem.getReferenceItems().size());
    }
}
