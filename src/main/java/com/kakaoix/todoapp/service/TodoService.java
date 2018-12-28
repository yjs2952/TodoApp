package com.kakaoix.todoapp.service;

import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.repository.TodoItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private TodoItemRepository todoItemRepository;

    public TodoService(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    public Page<TodoItem> getTodoList(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ?
                0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return todoItemRepository.findAll(pageable);
    }
}
