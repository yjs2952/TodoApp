package com.kakaoix.todoapp;

import com.kakaoix.todoapp.domain.Status;
import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class TodoappApplication  {

	@Autowired
	private TodoItemRepository todoItemRepository;

	/*@Override
	public void run(String... args) throws Exception {
		//List<TodoItem> list = todoItemRepository.findAll();
		*//*todoItemRepository.save(TodoItem.builder()
				.content("테스트")
				.isChecked(0)
				.referenceItems(Collections.singletonList(todoItemRepository.getOne(2L)))
				.status(Status.REF)
				.build());*//*

		//System.out.println(list.size());
	}*/

	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}

}

