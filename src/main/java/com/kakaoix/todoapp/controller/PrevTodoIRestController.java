package com.kakaoix.todoapp.controller;

import com.kakaoix.todoapp.service.TodoItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/prevtodos")
@RestController
public class PrevTodoIRestController {

    private TodoItemService todoService;

    public PrevTodoIRestController(TodoItemService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<?> getSearchTodoList(Long id, String keyword) {
        try {
            return ResponseEntity.ok(todoService.getSearchTodoList(id, keyword));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
