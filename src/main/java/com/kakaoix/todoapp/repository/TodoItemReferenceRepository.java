package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.domain.TodoReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoItemReferenceRepository extends JpaRepository<TodoReference, Long> {

    // 참조중인 TodoItem 관계 목록 조회
    @Query(value = "SELECT tr FROM TodoReference tr WHERE tr.currentTodoItem.id = :id ORDER BY tr.prevTodoItem.id")
    List<TodoReference> getListByCurrentId(@Param("id") Long id);

    // 자신을 참조하는 TodoItem 관계 목록 조회
    @Query(value = "SELECT tr FROM TodoReference tr WHERE tr.prevTodoItem.id = :id")
    List<TodoReference> getListByPrevId(@Param("id") Long id);

    // 참조중인 TodoItem이 있는지 여부 조회
    boolean existsTodoReferencesByCurrentTodoItemId(Long id);

    // 자신을 참조하는 TodoItem과의 참조관계 제거
    @Modifying
    @Query(value = "DELETE FROM TodoReference tr WHERE tr.prevTodoItem.id = :currentId")
    int deleteCurrentTodoItemsByPrevId(@Param("currentId") Long currentId);

    // 자신을 참조하는 TodoItem이 있는 지 여부 조회
    boolean existsTodoReferencesByPrevTodoItemId(Long id);

    // 선택한 참조 중인 TodoItem 제거
    @Modifying
    @Query(value = "DELETE FROM TodoReference tr WHERE tr.prevTodoItem.id IN :deleteIds AND tr.currentTodoItem.id = :currentId")
    int deletePrevTodoItemsByPrevIdAndCurrentId(@Param("deleteIds") List<Long> deleteIds, @Param("currentId") Long currentId);
}
