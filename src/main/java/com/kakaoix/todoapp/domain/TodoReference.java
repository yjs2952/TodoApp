package com.kakaoix.todoapp.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "todo_reference")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoReference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prev_todo_id")
    private TodoItem prevTodoItem;

    @ManyToOne
    @JoinColumn(name = "current_todo_id")
    private TodoItem currentTodoItem;
}
