package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    @Query(value = "SELECT t FROM TodoItem t WHERE t.id <> :id AND UPPER(t.content) LIKE CONCAT('%', UPPER(:keyword), '%') ORDER BY t.id DESC ")
    List<TodoItem> getTodoItemsByKeywordExceptSelf(@Param("id") Long id,@Param("keyword") String keyword);

    @Query(value = "SELECT t FROM TodoItem t WHERE t.id NOT IN :prevTodos AND UPPER(t.content) LIKE CONCAT('%', UPPER(:keyword), '%') ORDER BY t.id DESC ")
    List<TodoItem> getTodoItemsByKeywordExceptSelfAndRefs(@Param("prevTodos") List<Long> prevTodos,@Param("keyword") String keyword);
}
