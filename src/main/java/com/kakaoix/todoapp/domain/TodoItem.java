package com.kakaoix.todoapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todo_item")
@Getter
@Setter
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(length = 1, columnDefinition = "int default 0")
    private int isChecked;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modDate;
}
