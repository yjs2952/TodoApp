package com.kakaoix.todoapp.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity @Table(name = "todo_item")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(length = 1, columnDefinition = "int default 0")
    private Integer isChecked;

    @ManyToMany
    @JoinTable(name = "todo_reference",
                joinColumns = @JoinColumn(name = "id"),
                inverseJoinColumns = @JoinColumn(name = "todo_id"))
    private List<TodoItem> referenceItems;

    @Enumerated(EnumType.STRING)
    @Column(length = 4, nullable = false)
    private Status status;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime regDate;

    @Column
    private LocalDateTime modDate;
}
