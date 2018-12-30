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
    public Page<TodoItem> getTodoList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ?
                0 : pageable.getPageNumber() - 1, pageable.getPageSize());
        return todoItemRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public TodoItem findById(Long id) {
        return todoItemRepository.findById(id).orElse(new TodoItem());
    }

    @Transactional
    public Long addTodoItem(TodoItemDto todoItemDto) {
        TodoItem todoItem = todoItemRepository.save(TodoItem.builder()
                .content(todoItemDto.getContent())
                .status(Status.TODO)
                .regDate(LocalDateTime.now())
                .build());

        return todoItem.getId();
    }

    @Transactional
    public Long modifyTodoItem(Long id, TodoItemDto todoItemDto) {
        TodoItem getTodoItem = todoItemRepository.getOne(id);

        // TODO: 2018-12-30 : 참조 Todo가 있는지 확인해야 한다

        if (todoItemDto.getModifyType() == 1){
            getTodoItem.setIsChecked(todoItemDto.getIsChecked());
            getTodoItem.setModDate(LocalDateTime.now());
            return todoItemRepository.save(getTodoItem).getId();
        }

        // TODO: 2018-12-30 :
        return null;
    }

    @Transactional
    public void deleteTodoItem(Long id) {

        // TODO: 2018-12-30 참조하는 TodoItem이 있는지 검사

        todoItemRepository.deleteById(id);
    }
}
