package com.kakaoix.todoapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "todo_item")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: 2019-01-01 : validate 기능 작동은 하나 에러 메시지 반환이 안됨...... 
    @Size(max = 255, message = "최대 255자를 넘을 수 없습니다.")
    @NotEmpty(message = "Todo 명을 입력해 주세요.")
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
