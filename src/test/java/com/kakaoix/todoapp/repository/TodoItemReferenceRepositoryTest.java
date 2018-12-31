package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoItemReferenceRepositoryTest {

    @Autowired
    private TodoItemReferenceRepository repository;

    @Test
    public void 참조하는_todoItem_있는지_조회() {
        //List<TodoReference> list = repository.getListByCurrentId(4L);
        //Assert.assertEquals(4, list.size());
        Assert.assertTrue(repository.existsTodoReferencesByCurrentTodoItemId(4L));
    }

    @Test
    public void 참조받는_TodoItem_제거(){
//        int result = repository.deletePrevTodoItemsByCurrentId(7L);
//        log.info("result : {}", result);
        Assert.assertTrue(repository.existsTodoReferencesByPrevTodoItemId(1L));

    }
}
