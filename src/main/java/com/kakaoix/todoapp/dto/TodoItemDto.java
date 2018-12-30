package com.kakaoix.todoapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemDto {
    private Long id;
    private String content;
    private int isChecked;
    private List<Long> referenceIds;
    private int modifyType;
}
