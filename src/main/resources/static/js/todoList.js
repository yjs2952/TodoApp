function addTodoItem() {
    const content = $('#content').val();

    if (content === null || content === '') {
        alert('Todo item을 입력해 주세요.');
        $('#content').focus();
        return;
    }

    if (content.length > 255) {
        alert('255자를 넘을 수 없습니다.');
        $('#content').focus();
        return;
    }

    const jsonData = JSON.stringify({
        content: content
    });

    $.ajax({
        url: "/api/todos",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "text",
        success: function (data) {
            alert(data);
            location.href = '/';
        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
        }
    });
}

//date객체 YYYY-MM-DD 변환함수
function dateToYYYYMMDD(date) {
    if (date == null) return "";
    function pad(num) {
        num = num + '';
        return num.length < 2 ? '0' + num : num;
    }
    return date.getFullYear() + '-' + pad(date.getMonth() + 1) + '-' + pad(date.getDate());
}

// 조회한 todo목록 나열
function getSearchTodoItems(data) {
    let searchTodoItems = ``;
    for (let i = 0; i < data.length; i++) {
        searchTodoItems +=
            `<li class="list-group-item">
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" name="searchTodo" value="${data[i].id}"
                               id="searchTodo${data[i].id}">
                        <label style="width: 90%" class="custom-control-label custom-control-inline"
                               for="searchTodo${data[i].id}" id="searchTodoContent${data[i].id}">${data[i].id}&nbsp&nbsp ${data[i].content}</label>
                    </div>
                </li>`
    }
    $('#searchList').html(searchTodoItems);
}

function getPrevTodoItemList(prevIds) {
    let checkListHtml = ``;
    for (let i = 0; i < prevIds.length; i++) {
        checkListHtml +=
            `<div class="custom-control custom-control-inline custom-checkbox ">
                    <input type="checkbox" class="custom-control-input"
                        id="checkRefId${prevIds[i]}" name="refTodo" value="${prevIds[i]}">
                    <label style="width: 100%" class="custom-control-label"
                        for="checkRefId${prevIds[i]}" id="refId">${prevIds[i]}</label>
                </div>`
    }
    return checkListHtml;
}

$('#content').on("keypress", function (e) {
    if (e.keyCode === 13) {
        addTodoItem();
    }
});

// todoItem 수정
$('#modifyButton').on("click", function () {
    const id = $(this).parents("div[data-id]").attr('data-id');
    const modifyContent = $('#modifyContent').val();
    const refTodos = $('input:checkbox[name="refTodo"]:checked');
    const searchTodos = $('input:checkbox[name="searchTodo"]:checked');

    if (modifyContent === null || modifyContent === '') {
        alert('Todo item을 입력해 주세요.');
        $('#modifyContent').focus();
        return;
    }

    if (modifyContent.length > 255) {
        alert('255자를 넘을 수 없습니다.');
        $('#modifyContent').focus();
        return;
    }

    let insertRefs = [];
    let deleteRefs = [];

    searchTodos.each(function () {
        insertRefs.push($(this).val());
    });

    refTodos.each(function () {
        deleteRefs.push($(this).val());
    });

    const jsonData = JSON.stringify({
        id: id,
        content: modifyContent,
        prevIds: insertRefs,
        deleteIds: deleteRefs
    });

    $.ajax({
        url: "/api/todos/" + id,
        type: "PUT",
        data: jsonData,
        contentType: "application/json",
        dataType: "text",
        success: function (data) {
            alert(data);

            const page = $('#todo-content').attr('data-pno');
            location.href = '/?page=' + page;
        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
        }
    });
});

$('#addTodo').on("click", function () {
    addTodoItem();
});

$('#searchTodoItem').on("keypress", function (e) {
    if (e.keyCode === 13) {
        const id = $(this).parents("div[data-id]").attr('data-id');
        const keyword = $(this).val();

        $('#searchList').html('');

        $.ajax({
            url: "/api/prevtodos",
            type: "GET",
            data: {
                keyword: keyword,
                id: id
            },
            success: function (data) {
                if (data != null && data.length > 0) {
                    getSearchTodoItems(data);
                }
            },
            error: function (request, status, error) {
                alert(request.responseText + "\n");
            }
        });
    }
});

$('.modifyModalButton').on("click", function () {
    let searchList = $('#searchList');
    let prevTodoItemListDiv = $('#check-list-box');
    let modifyContent = $('#modifyContent');
    let dateHtml = $('#date');

    // 모달창 내부 값 초기화
    searchList.html('');
    prevTodoItemListDiv.html('');
    modifyContent.val('');

    const id = $(this).parents("div[data-tno]").attr('data-tno');
    $.ajax({
        url: "/api/todos/" + id,
        type: "GET",
        success: function (data) {
            let regDate = `<div>작성일 : ${dateToYYYYMMDD(new Date(data['regDate']))}</div>`;
            let modDate = data['modDate'] == null ? '' : `<div>수정일 : ${dateToYYYYMMDD(new Date(data['modDate']))}</div>`;

            modifyContent.val(data.content);
            dateHtml.html(`${regDate}${modDate}`);

            let prevIds = data['prevIds'];

            // 참조된 todo가 있는지 확인
            if (prevIds != null && prevIds.length > 0) {
                prevTodoItemListDiv.html(getPrevTodoItemList(prevIds));
            }
            let modifyForm = $('#modifyModal');
            modifyForm.attr('data-id', id);
            modifyForm.modal('show');
        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
        }
    });
});

$('.todoCheck').on("change", function () {
    const id = $(this).parents("div[data-tno]").attr('data-tno');
    const isChecked = $(this).prop('checked') === true ? 1 : 0;
    const jsonData = JSON.stringify({
        isChecked: isChecked,
        modifyType: 1   // modifyType (1 : 체크박스 선택시, 0 : 수정 모달 버튼 클릭시)
    });

    $.ajax({
        url: "/api/todos/" + id,
        data: jsonData,
        type: "PUT",
        contentType: "application/json",
        dataType: "text",
        success: function (data) {
            alert(data);

            const page = $('#todo-content').attr('data-pno');
            location.href = '/?page=' + page;
        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
            $(`input:checkbox[id="check${id}"]`).prop('checked', false);
        }
    })
});

$('.deleteButton').on("click", function () {
    const id = $(this).parent().attr('data-tno');
    $.ajax({
        url: "/api/todos/" + id,
        type: "DELETE",
        success: function (data) {
            alert(data);

            const page = $('#todo-content').attr('data-pno');
            location.href = '/?page=' + page;
        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
        }
    })
});
