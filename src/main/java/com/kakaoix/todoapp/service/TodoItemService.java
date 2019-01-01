package com.kakaoix.todoapp.service;

import com.kakaoix.todoapp.domain.Status;
import com.kakaoix.todoapp.domain.TodoItem;
import com.kakaoix.todoapp.domain.TodoReference;
import com.kakaoix.todoapp.dto.TodoItemDto;
import com.kakaoix.todoapp.repository.TodoItemReferenceRepository;
import com.kakaoix.todoapp.repository.TodoItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoItemService {

    private final TodoItemRepository todoItemRepository;
    private final TodoItemReferenceRepository todoItemReferenceRepository;

    @Transactional(readOnly = true)
    public Page<TodoItem> getTodoList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ?
                0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "id");
        return todoItemRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<TodoItem> getSearchTodoList(Long id, String keyword){
        return todoItemRepository.getTodoItemsByKeywordExceptSelf(id, keyword);
    }

    @Transactional(readOnly = true)
    public TodoItemDto getModifyTodoItem(Long id){
        TodoItem todoItem = todoItemRepository.getOne(id);

        // TodoDto 에 값 세팅
        TodoItemDto todoItemDto = TodoItemDto.builder()
                .id(id)
                .content(todoItem.getContent())
                .isChecked(todoItem.getIsChecked())
                .status(todoItem.getStatus())
                .regDate(todoItem.getRegDate())
                .modDate(todoItem.getModDate())
                .build();

        // 참조 todo 목록 불러오기
        List<TodoReference> prevTodoItems = todoItemReferenceRepository.getListByCurrentId(id);

        if (prevTodoItems.size() > 0) {
            List<Long> prevItemIds = new ArrayList<>();
            for (TodoReference todo : prevTodoItems) {
                prevItemIds.add(todo.getPrevTodoItem().getId());
            }
            todoItemDto.setReferenceIds(prevItemIds);
        }

        log.info("todo content : {}", todoItem.getContent());

        return todoItemDto;
    }

    @Transactional
    public Long addTodoItem(TodoItemDto todoItemDto) {
        TodoItem todoItem = todoItemRepository.save(TodoItem.builder()
                .content(todoItemDto.getContent())
                .status(Status.TODO)
                .regDate(LocalDateTime.now())
                .build());
        return todoItem.getId();
    }

    @Transactional
    public String checkTodoItem(Long id, TodoItemDto todoItemDto) throws Exception {
        TodoItem getTodoItem = todoItemRepository.getOne(id);
        if (getTodoItem.getStatus().equals(Status.REF))
            throw new Exception("참조하는 Todo 항목이 있습니다.");

        getTodoItem.setIsChecked(todoItemDto.getIsChecked());
        getTodoItem.setModDate(LocalDateTime.now());

        if (todoItemDto.getIsChecked() == 1) {
            getTodoItem.setStatus(Status.DONE);
            return "완료 처리 되었습니다.";
        }

        getTodoItem.setStatus(Status.TODO);
        return "미완료 처리 되었습니다.";
    }

    @Transactional
    public String modifyTodoItem(Long id, TodoItemDto todoItemDto) throws Exception {
        TodoItem getTodoItem = todoItemRepository.getOne(id);

        // TODO: 2018-12-30 : 참조 Todo가 있는지 확인해야 한다


        // 수정 버튼 클릭시
        getTodoItem.setContent(todoItemDto.getContent());
        getTodoItem.setModDate(LocalDateTime.now());

            /*if (todoItemDto.getReferenceIds().size() > 0) {
                getTodoItem.setStatus(Status.REF);
            }*/

    // TODO: 2018-12-30 :
        return null;
}

    @Transactional
    public void deleteTodoItem(Long id) throws Exception{

        // TODO: 2018-12-30 참조하는 TodoItem이 있는지 검사
        if (todoItemReferenceRepository.existsTodoReferencesByCurrentTodoItemId(id)) {
            throw new Exception("참조하는 Todo 항목이 있습니다.");
        }

        // 자신을 참조하는 TodoItem 들과의 참조관계 삭제
        if (todoItemReferenceRepository.existsTodoReferencesByPrevTodoItemId(id)) {
            todoItemReferenceRepository.deletePrevTodoItemsByCurrentId(id);
        }

        todoItemRepository.deleteById(id);
    }
}
