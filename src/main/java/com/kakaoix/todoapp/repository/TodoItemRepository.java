package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    // 키워드로 참조하려는 TodoItem 조회 (자기자신과 현재 참조중인 TodoItem, 자신을 참조하는 TodoItem 제외)
    @Query(value = "SELECT t FROM TodoItem t WHERE t.id <> :id AND UPPER(t.content) LIKE CONCAT('%', UPPER(:keyword), '%') " +
            "AND NOT EXISTS (SELECT 1 FROM TodoReference tr WHERE (t.id = tr.currentTodoItem.id AND tr.prevTodoItem.id = :id) OR (t.id = tr.prevTodoItem.id AND tr.currentTodoItem.id = :id)) ORDER BY t.id DESC ")
    List<TodoItem> getTodoItemsByKeyword(@Param("id") Long id, @Param("keyword") String keyword);
}
