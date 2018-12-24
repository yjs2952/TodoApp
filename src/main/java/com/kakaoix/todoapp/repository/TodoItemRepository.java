package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    //List<TodoItem> getTodoList
}
