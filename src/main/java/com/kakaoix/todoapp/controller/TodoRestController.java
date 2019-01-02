package com.kakaoix.todoapp.controller;

import com.kakaoix.todoapp.dto.TodoItemDto;
import com.kakaoix.todoapp.service.TodoItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/todos")
@RestController
public class TodoRestController {

    private TodoItemService todoService;

    public TodoRestController(TodoItemService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTodoList(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(todoService.getTodoList(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTodoItem(@PathVariable("id") Long id) {
        return ResponseEntity.ok(todoService.getModifyTodoItem(id));
    }

    @PostMapping
    public ResponseEntity<?> addTodoItem(@RequestBody TodoItemDto todoItemDto) {

        ResponseEntity<?> entity = null;
        try {
            todoService.addTodoItem(todoItemDto);
            entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyTodoItem(@PathVariable("id") Long id, @RequestBody TodoItemDto todoItemDto) {

        ResponseEntity<?> entity = null;
        try {
            String message = null;

            if (todoItemDto.getModifyType() == 1) {
                message = todoService.checkTodoItem(id, todoItemDto);
            } else {
                message = todoService.modifyTodoItem(id, todoItemDto);
            }

            entity = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id) {
        ResponseEntity<?> entity = null;
        try {
            todoService.deleteTodoItem(id);
            entity = new ResponseEntity<>("삭제 되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
