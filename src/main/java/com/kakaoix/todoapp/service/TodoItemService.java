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

    /**
     * 전체 TodoItem 목록 가져오기
     *
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<TodoItem> getTodoList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ?
                0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "id");
        Page<TodoItem> todoList = todoItemRepository.findAll(pageable);

        // 참조하는 TodoItem의 id값을 json 데이터로 전달하기 위해 List에 담는다.
        for (TodoItem todoItem : todoList.getContent()) {
            List<TodoReference> prevTodoList = todoItemReferenceRepository.getListByCurrentId(todoItem.getId());
            if (prevTodoList.size() > 0) {
                List<Long> prevIds = new ArrayList<>();
                for (TodoReference todoReference : prevTodoList) {
                    prevIds.add(todoReference.getPrevTodoItem().getId());
                }
                todoItem.setPrevTodoIds(prevIds);
            }
        }

        return todoList;
    }

    /**
     * 참조하려는 TodoItem 목록 조회
     *
     * @param id
     * @param keyword
     * @return
     */
    @Transactional(readOnly = true)
    public List<TodoItem> getSearchTodoList(Long id, String keyword) {
        /*List<TodoReference> prevTodoItems = todoItemReferenceRepository.getListByCurrentId(id);

        if (prevTodoItems.size() > 0) {
            List<Long> exceptItemIds = new ArrayList<>();
            for (TodoReference todo : prevTodoItems) {
                exceptItemIds.add(todo.getPrevTodoItem().getId());
            }
            exceptItemIds.add(id);
            return todoItemRepository.getTodoItemsByKeywordExceptSelfAndRefs(exceptItemIds, keyword);
        }*/
        return todoItemRepository.getTodoItemsByKeyword(id, keyword);
    }

    /**
     * 수정하려는 TodoItem 정보 조회
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public TodoItemDto getModifyTodoItem(Long id) {
        TodoItem todoItem = todoItemRepository.getOne(id);

        // TotoItem 의 값을 TodoItemDto에 전달
        TodoItemDto todoItemDto = TodoItemDto.builder()
                .id(id)
                .content(todoItem.getContent())
                .isChecked(todoItem.getIsChecked())
                .status(todoItem.getStatus())
                .regDate(todoItem.getRegDate())
                .modDate(todoItem.getModDate())
                .build();

        // 참조하는 TodoItem이 있는지 조회
        List<TodoReference> prevTodoItems = todoItemReferenceRepository.getListByCurrentId(id);

        // 참조하는 TodoItem이 있는 경우 해당 TodoItem들의 id값을 TodoItemDto에 전달
        if (prevTodoItems.size() > 0) {
            List<Long> prevItemIds = new ArrayList<>();
            for (TodoReference todo : prevTodoItems) {
                prevItemIds.add(todo.getPrevTodoItem().getId());
            }
            todoItemDto.setPrevIds(prevItemIds);
        }
        return todoItemDto;
    }

    /**
     * 새 TodoItem 추가
     *
     * @param todoItemDto
     * @return
     */
    @Transactional
    public Long addTodoItem(TodoItemDto todoItemDto) {
        TodoItem todoItem = todoItemRepository.save(TodoItem.builder()
                .content(todoItemDto.getContent())
                .isChecked(0)
                .status(Status.TODO)
                .regDate(LocalDateTime.now())
                .build());
        return todoItem.getId();
    }

    /**
     * TodoItem 완료여부 체크
     *
     * @param id
     * @param todoItemDto
     * @return
     * @throws Exception
     */
    @Transactional
    public String checkTodoItem(Long id, TodoItemDto todoItemDto) throws Exception {

        List<TodoReference> prevTodoItemList = todoItemReferenceRepository.getListByCurrentId(id);

        // 참조하는 TodoItem이 있는지 조회
        for (TodoReference prevTodoItem : prevTodoItemList) {
            if (prevTodoItem.getPrevTodoItem().getIsChecked() == 0) {
                throw new Exception("완료되지 않은 참조 Todo가 있습니다.");
            }
        }

        TodoItem getTodoItem = todoItemRepository.getOne(id);
        getTodoItem.setIsChecked(todoItemDto.getIsChecked());
        getTodoItem.setModDate(LocalDateTime.now());

        // 완료 처리
        if (todoItemDto.getIsChecked() == 1) {
            getTodoItem.setStatus(Status.DONE);
            return "완료 처리 되었습니다.";
        }

        // 자신을 참조하면서 완료상태인 Todo가 있는지 확인 (체크 해제할 때만 실행되야함)
        for (TodoReference todoReference : todoItemReferenceRepository.getListByPrevId(id)) {

            // 참조하는 TodoItem이 모두 완료상태일 때만 완료할 수 있기 때문에 참조상태로 다시 변경
            if (todoReference.getCurrentTodoItem().getIsChecked() == 1) {
                TodoItem todoItem = todoReference.getCurrentTodoItem();
                todoItem.setIsChecked(0);
                todoItem.setStatus(Status.REF);
                todoItemRepository.save(todoItem);
            }
        }

        // 미완료 처리
        getTodoItem.setStatus(Status.TODO);
        if (prevTodoItemList.size() > 0) getTodoItem.setStatus(Status.REF);
        
        return "미완료 처리 되었습니다.";
    }

    /**
     * TodoITem 수정
     *
     * @param id
     * @param todoItemDto
     * @return
     * @throws Exception
     */
    @Transactional
    public String modifyTodoItem(Long id, TodoItemDto todoItemDto) {
        TodoItem getTodoItem = todoItemRepository.getOne(id);

        // 삭제할 prevTodoItem 있는지 확인
        if (todoItemDto.getDeleteIds().size() > 0) {
            todoItemReferenceRepository.deletePrevTodoItemsByPrevIdAndCurrentId(todoItemDto.getDeleteIds(), id);
            todoItemReferenceRepository.flush();    // 삭제 후 남은 참조가 있는지 확인하기 위해 DB에 반영 (lazy loading 방지)

            // 참조하는 TodoItem가 남아있는지 조회
            if (todoItemReferenceRepository.getListByCurrentId(id).size() <= 0) {
                getTodoItem.setStatus(Status.TODO);
            }
        }

        // 추가할 prevTodoItem이 있는지 확인
        if (todoItemDto.getPrevIds().size() > 0) {
            IntStream.rangeClosed(0, todoItemDto.getPrevIds().size() - 1).forEach(index -> {
                        todoItemReferenceRepository.save(TodoReference.builder()
                                .currentTodoItem(todoItemRepository.getOne(id))
                                .prevTodoItem(todoItemRepository.getOne(todoItemDto.getPrevIds().get(index)))
                                .build());
                    }
            );
            todoItemDto.setStatus(Status.REF);
        }
        getTodoItem.setContent(todoItemDto.getContent());
        getTodoItem.setModDate(LocalDateTime.now());

        return "수정 완료 하였습니다.";
    }

    /**
     * TodoItem 삭제
     *
     * @param id
     * @throws Exception
     */
    @Transactional
    public void deleteTodoItem(Long id) throws Exception {

        if (todoItemReferenceRepository.existsTodoReferencesByCurrentTodoItemId(id)) {
            throw new Exception("참조하는 Todo 항목이 있습니다.");
        }

        // 자신을 참조하는 TodoItem 과의 참조관계 제거
        if (todoItemReferenceRepository.existsTodoReferencesByPrevTodoItemId(id)) {
            todoItemReferenceRepository.deletePrevTodoItemsByCurrentId(id);
        }

        todoItemRepository.deleteById(id);
    }
}
