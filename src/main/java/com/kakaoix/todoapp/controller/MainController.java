package com.kakaoix.todoapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class MainController {

    @GetMapping("")
    public String todoList(){
        log.info("<<<<<Start MainController>>>>>");
        return "todoList";
    }
}
