package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.domain.TodoReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoItemReferenceRepository extends JpaRepository<TodoReference, Long> {

    @Query(value = "SELECT tr FROM TodoReference tr WHERE tr.currentTodoItem.id = :id ORDER BY tr.prevTodoItem.id")
    List<TodoReference> getListByCurrentId(@Param("id") Long id);

    @Query(value = "SELECT tr FROM TodoReference tr WHERE tr.prevTodoItem.id = :id")
    List<TodoReference> getListByPrevId(@Param("id") Long id);

    boolean existsTodoReferencesByCurrentTodoItemId(Long id);

    @Modifying
    @Query(value = "DELETE FROM TodoReference tr WHERE tr.prevTodoItem.id = :currentId")
    int deletePrevTodoItemsByCurrentId(@Param("currentId") Long currentId);

    boolean existsTodoReferencesByPrevTodoItemId(Long id);

    @Modifying
    @Query(value = "DELETE FROM TodoReference tr WHERE tr.prevTodoItem.id IN :deleteIds AND tr.currentTodoItem.id = :currentId")
    int deletePrevTodoItemsByPrevIdAndCurrentId(@Param("deleteIds") List<Long> deleteIds, @Param("currentId") Long currentId);
}
