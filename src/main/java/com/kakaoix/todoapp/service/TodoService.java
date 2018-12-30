package com.kakaoix.todoapp.service;

import com.kakaoix.todoapp.domain.Status;
import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.dto.TodoItemDto;
import com.kakaoix.todoapp.repository.TodoItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TodoService {

    private TodoItemRepository todoItemRepository;

    public TodoService(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @Transactional(readOnly = true)
    public Page<TodoItem> getTodoList(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ?
                0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return todoItemRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public TodoItem findById(Long id){
        return todoItemRepository.findById(id).orElse(new TodoItem());
    }

    @Transactional
    public Long addTodoItem(TodoItemDto todoItemDto){
        TodoItem todoItem = todoItemRepository.save(TodoItem.builder()
                .content(todoItemDto.getContent())
                .status(Status.TODO)
                .regDate(LocalDateTime.now())
                .build());

        return todoItem.getId();
    }

    @Transactional
    public void deleteTodoItem(Long id) {



        todoItemRepository.deleteById(id);
    }
}
