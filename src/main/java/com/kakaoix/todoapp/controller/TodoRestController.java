package com.kakaoix.todoapp.controller;

import com.kakaoix.todoapp.dto.TodoItemDto;
import com.kakaoix.todoapp.service.TodoItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/todos")
@RestController
public class TodoRestController {

    private TodoItemService todoService;
    private final static int ZERO = 0;

    public TodoRestController(TodoItemService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<?> getTodoItemList(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(todoService.getTodoList(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTodoItem(@PathVariable("id") Long id) {
        return ResponseEntity.ok(todoService.getModifyTodoItem(id));
    }

    @PostMapping
    public ResponseEntity<?> addTodoItem(@Valid @RequestBody TodoItemDto todoItemDto, BindingResult bindingResult) {

        // validation 체크
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(ZERO).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        try {
            todoService.addTodoItem(todoItemDto);
            return new ResponseEntity<>("추가 되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyTodoItem(@PathVariable("id") Long id, @Valid @RequestBody TodoItemDto todoItemDto, BindingResult bindingResult) {

        // validation 체크
        if(bindingResult.hasErrors()){
            String errorMessage = bindingResult.getAllErrors().get(ZERO).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        try {
            String message = null;

            if (todoItemDto.getModifyType() == 1) {     // 완료 여부 체크(1) 인지 수정 버튼 클릭(0)인지
                message = todoService.checkTodoItem(id, todoItemDto);
            } else {
                message = todoService.modifyTodoItem(id, todoItemDto);
            }

            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("id") Long id) {
        try {
            todoService.deleteTodoItem(id);
            return new ResponseEntity<>("삭제 되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
