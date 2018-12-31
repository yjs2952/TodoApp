package com.kakaoix.todoapp.repository;

import com.kakaoix.todoapp.domain.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    List<TodoItem> getTodoItemsByIdIn(List<Long> referenceIds);

    // TODO: 2018-12-31 : 참조 관계 삭제 메소드 만들어야함  (prev_id = ?)

    // TODO: 2018-12-31 : 참조 관계 삭제 후 본체 삭제하는 메소드 만들어야 함
}
