package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.Status;
import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.domain.TodoReference;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoItemReferenceRepositoryTest {

    @Autowired
    private TodoItemReferenceRepository repository;

    @Test
    public void 참조하는_todoItem_있는지_조회() {
        List<TodoReference> list = repository.getListByCurrentId(4L);
        for (TodoReference tr : list) {
            log.info("currnet id : {}", tr.getCurrentTodoItem().getId());
        }

        Assert.assertTrue(repository.existsTodoReferencesByCurrentTodoItemId(4L));
    }

    @Test
    public void 자신을_참조하는_TodoItem_제거() {
        Assert.assertTrue(repository.existsTodoReferencesByPrevTodoItemId(1L));
    }

    @Test
    public void 참조중인_TodoItem_제거() {
        int delCount = repository.deletePrevTodoItemsByPrevIdAndCurrentId(Arrays.asList(1L, 2L, 3L), 4L);
        Assert.assertEquals(3, delCount);
    }

    @Test
    public void 자신을_참조하는_TodoItem_조회() {
        List<TodoReference> todoReferences = repository.getListByPrevId(22L);

        for (TodoReference tr : todoReferences) {
            log.info("나를 참조하는 id : {}, 상태 : {}\n", tr.getCurrentTodoItem().getId(), tr.getCurrentTodoItem().getStatus());

            if (repository.getListByCurrentId(tr.getCurrentTodoItem().getId()).size() == 1) {
                tr.getCurrentTodoItem().setStatus(Status.TODO);
                log.info("하나 남았으므로 상태 변경 : {}", tr.getCurrentTodoItem().getStatus());
            }
            for (TodoReference ctr : repository.getListByCurrentId(tr.getCurrentTodoItem().getId())) {
                log.info("나를 참조하는 TodoItem이 참조하는 id : {}\n", ctr.getPrevTodoItem().getId());
            }
        }
    }
}
