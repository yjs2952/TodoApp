package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.domain.TodoReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoItemReferenceRepository extends JpaRepository<TodoReference, Long> {

    List<TodoReference> getTodoItemsByIdIn(List<Long> referenceIds);

    List<TodoReference> findTodoReferencesByCurrentTodoItem_Id(Long currentId);

    @Query(value = "SELECT tr FROM TodoReference tr WHERE tr.currentTodoItem.id = :id")
    List<TodoReference> getListByCurrentId(@Param("id") Long id);

    boolean existsTodoReferencesByCurrentTodoItemId(Long id);

    List<TodoReference> findTodoReferencesByCurrentTodoItemAndPrevTodoItem(Long prevId, Long currentId);

    // TODO: 2018-12-31 : 전체 참조 관계 삭제 메소드 (본체 삭제를 위해) (prev_id = ?)
    @Modifying
    @Query(value = "DELETE FROM TodoReference tr WHERE tr.prevTodoItem.id = :currentId")
    int deletePrevTodoItemsByCurrentId(@Param("currentId") Long currentId);

    boolean existsTodoReferencesByPrevTodoItemId(Long id);

    // TODO: 2018-12-31 : 단일 참조 관계 삭제 메소드 (참조만 끊기 위해) (prev_id = ? and current_id = ?)
}
