package com.kakaoix.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kakaoix.todoapp.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TodoItemDto {
    private Long id;
    private String content;
    private int isChecked;
    private Status status;
    private List<Long> referenceIds;
    private String keyword;
    private int modifyType;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
