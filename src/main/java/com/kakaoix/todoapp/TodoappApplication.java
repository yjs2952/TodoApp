package com.kakaoix.todoapp;

import com.kakaoix.todoapp.domain.Status;
import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class TodoappApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoappApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner commandLineRunner(TodoItemRepository todoItemRepository) throws Exception {
        return (args) -> {
            List<TodoItem> todoItems = new ArrayList<>();
            todoItems.add(todoItemRepository.getOne(2L));
            todoItems.add(todoItemRepository.getOne(3L));

            IntStream.rangeClosed(1, 100).forEach(index -> {
                        todoItemRepository.save(TodoItem.builder()
                                .content("테스트" + index)
                                .isChecked(1)
                                //.referenceItems(todoItems)
                                .status(Status.REF)
                                .regDate(LocalDateTime.now())
                                .modDate(LocalDateTime.now())
                                .build());
                    }
            );
        };
    }*/
}

