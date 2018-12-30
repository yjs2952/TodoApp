package com.kakaoix.todoapp.controller;

import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.dto.TodoItemDto;
import com.kakaoix.todoapp.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RequestMapping("/api/todos")
@RestController
public class TodoRestController {

    private TodoService todoService;

    public TodoRestController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTodoList(@PageableDefault Pageable pageable){
        Page<TodoItem> todoItems = todoService.getTodoList(pageable);
        PageMetadata pageMetadata =
                new PageMetadata(pageable.getPageSize(), todoItems.getNumber(), todoItems.getTotalElements());
        PagedResources<TodoItem> resources = new PagedResources<>(todoItems.getContent(), pageMetadata);
        resources.add(linkTo(methodOn(TodoRestController.class).getTodoList(pageable)).withSelfRel());
        return ResponseEntity.ok(resources);
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
}
