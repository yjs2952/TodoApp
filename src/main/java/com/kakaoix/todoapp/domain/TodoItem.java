package com.kakaoix.todoapp.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "todo_item")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(length = 1, columnDefinition = "int default 0")
    private Integer isChecked;

    @Enumerated(EnumType.STRING)
    @Column(length = 4, nullable = false)
    private Status status;

    @Column
    private LocalDateTime regDate;

    @Column
    private LocalDateTime modDate;

    @Transient
    private List<Long> prevTodoIds;
}