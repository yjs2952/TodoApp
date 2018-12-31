package com.kakaoix.todoapp.controller;

import com.kakaoix.todoapp.service.TodoService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String home(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("todoList", todoService.getTodoList(pageable));
        return "todoList";
    }
}
