package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

}
