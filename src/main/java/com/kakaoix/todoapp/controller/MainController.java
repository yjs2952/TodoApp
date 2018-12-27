package com.kakaoix.todoapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MainController {

    @GetMapping("/")
    public String todoList(){
        return "todoList";
    }

    @GetMapping("/{name}.html")
    public String page(@PathVariable String name, Model model) {
        model.addAttribute("pageName", name);
        return "page";
    }
}
