getTodoItemList();

function getTodoItemList(page) {
    $.ajax({
        url: "/api/todos",
        type: "GET",
        data: {page: page},
        dataType: "json",
        success: function (data) {
            let todoContent = $('#todo-content');
            todoContent.attr('data-pno', data['number'] + 1);

            let todoItemHtml = ``;
            for (let i = 0; i < data['content'].length; i++) {
                todoItemHtml += getTodoItemHtml(data['content'][i]);
            }
            todoContent.html(todoItemHtml);
            $('#pagination').html(getPaginationHtml(data))

        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
        }
    });
}

function addTodoItem() {
    const content = $('#content');

    if (content.val() === null || content.val() === '') {
        alert('Todo item 을 입력해 주세요.');
        content.focus();
        return;
    }

    if (content.val().length > 255) {
        alert('255자를 넘을 수 없습니다.');
        content.focus();
        return;
    }

    const jsonData = JSON.stringify({
        content: content.val()
    });

    $.ajax({
        url: "/api/todos",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function (data) {
            alert(data);
            getTodoItemList();
            //location.href = '/';
        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
        }
    });
}

function getPaginationHtml(data) {

    let currentPage = data['number'];
    let totalPages = data['totalPages'];
    let startPage = Math.floor(currentPage / 10) * 10 + 1;
    let endPage = totalPages > startPage + 9 ? startPage + 9 : totalPages;
    let isFisrt = data['first'];
    let isLast = data['last'];


    let previousHtml =
        `<li class="page-item ${currentPage === 0 ? 'disabled' : ''}">
                <div class="page-link" aria-label="Previous" onclick="getTodoItemList(1)">&laquo;</div>
        </li>
        <li class="page-item" style="${isFisrt ? 'display:none' : ''}">
            <div class="page-link" onclick="getTodoItemList(${currentPage})">&lsaquo;</div>
        </li>`;

    let pageListHtml = ``;

    for (let i = startPage; i <= endPage; i++) {
        pageListHtml +=
            `<li class="page-item ${currentPage + 1 === i ? 'active' : ''}">
                <div onclick="getTodoItemList(${i})">
                    <span class="page-link"> ${i}
                        <span class="sr-only">(current)</span>
                    </span>
                </div>
            </li>`;
    }

    let nextHtml =
        `<li class="page-item" style="${isLast ? 'display:none' : ''}">
                <div class="page-link" onclick="getTodoItemList(${currentPage}+2)">&rsaquo;</div>
        </li>
        <li class="page-item ${currentPage === totalPages - 1 ? 'disabled' : ''}">
            <div class="page-link" onclick="getTodoItemList(${totalPages})" aria-label="Next">&raquo;</div>
        </li>`;

    return previousHtml + pageListHtml + nextHtml;
}

function getTodoItemHtml(data) {

    const id = data['id'];
    const prevTodoItem = data['prevTodoIds'];
    const modDate = data['modDate'] === null ? '' : `<div>수정일 : ${dateToYYYYMMDD(new Date(data['modDate']))}</div>`;
    const checked = data['isChecked'] === 1 ? 'checked' : '';

    let prevTodoItemsHtml = ``;
    if (prevTodoItem != null) {
        for (let i = 0; i < prevTodoItem.length; i++) {
            prevTodoItemsHtml +=
                `<span class="mr-2">@${prevTodoItem[i]}</span>`;
        }
    }

    return `<div data-tno="${id}" class="alert alert-success d-flex flex-row align-items-center">
                <div class="custom-control custom-checkbox mr-2">
                    <input type="checkbox" class="todoCheck custom-control-input"
                           id="check${id}"
                           ${checked} onclick="checkTodoItem(${id})">
                    <label class="custom-control-label" for="check${id}"></label>
                </div>
                <div class="mr-3">${id}</div>
                <div class="todo-text text-center">
                    <div class="${data['isChecked'] === 1 ? 'checked' : ''}">
                        ${data['content']}
                    </div>
                    <div>
                        ${prevTodoItemsHtml}
                    </div>
                </div>

                <div class="mx-5">
                    <div>작성일 : ${dateToYYYYMMDD(new Date(data['regDate']))}</div>
                    ${modDate}
                </div>
                <button class="close far fa-edit fa-1x mx-2" onclick="showModifyModal(${data['id']})"></button>
                <button class="close far fa-trash-alt fa-1x" onclick="deleteTodoItem(${data['id']})"></button>
            </div>`;
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


function showModifyModal(id) {
    let prevTodoItemListDiv = $('#check-list-box');
    let modifyContent = $('#modifyContent');

    // 모달창 내부 값 초기화
    $('#searchList').html('');
    prevTodoItemListDiv.html('');

    $.ajax({
        url: "/api/todos/" + id,
        type: "GET",
        success: function (data) {
            let regDate = `<div>작성일 : ${dateToYYYYMMDD(new Date(data['regDate']))}</div>`;
            let modDate = data['modDate'] == null ? '' : `<div>수정일 : ${dateToYYYYMMDD(new Date(data['modDate']))}</div>`;

            modifyContent.val(data.content);
            $('#modalLabel').html(`${data.id}  Todo [${data.status === 'TODO' ? '미완료' : (data.status === 'DONE' ? '완료' : '참조')}]`);
            $('#date').html(`${regDate}${modDate}`);

            let prevIds = data['prevIds'];

            // 참조된 TodoItem 이 있는지 확인
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
}

function checkTodoItem(id) {
    const isChecked = $('#check' + id).prop('checked') === true ? 1 : 0;
    console.log(isChecked);
    const jsonData = JSON.stringify({
        isChecked: isChecked,
        modifyType: 1   // modifyType (1 : 완료여부 체크박스 선택시, 0 : 수정 버튼 클릭시)
    });

    $.ajax({
        url: "/api/todos/" + id,
        data: jsonData,
        type: "PUT",
        contentType: "application/json",
        success: function (data) {
            alert(data);

            const page = $('#todo-content').attr('data-pno');
            getTodoItemList(page);
        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
            $(`input:checkbox[id="check${id}"]`).prop('checked', false);
        }
    })
}

function deleteTodoItem(id) {
    $.ajax({
        url: "/api/todos/" + id,
        type: "DELETE",
        success: function (data) {
            alert(data);

            const page = $('#todo-content').attr('data-pno');
            getTodoItemList(page);
        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
        }
    })
}


$('#content').on("keypress", function (e) {
    if (e.keyCode === 13) {
        addTodoItem();
    }
});

$('#addTodo').on("click", function () {
    addTodoItem();
});

// todoItem 수정
$('#modifyButton').on("click", function () {
    const id = $(this).parents("div[data-id]").attr('data-id');
    const modifyContent = $('#modifyContent');
    const refTodos = $('input:checkbox[name="refTodo"]:checked');
    const searchTodos = $('input:checkbox[name="searchTodo"]:checked');

    if (modifyContent.val() === null || modifyContent.val() === '') {
        alert('Todo item 을 입력해 주세요.');
        modifyContent.focus();
        return;
    }

    if (modifyContent.val().length > 255) {
        alert('255자를 넘을 수 없습니다.');
        modifyContent.focus();
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
        content: modifyContent.val(),
        prevIds: insertRefs,
        deleteIds: deleteRefs
    });

    $.ajax({
        url: "/api/todos/" + id,
        type: "PUT",
        data: jsonData,
        contentType: "application/json",
        success: function (data) {
            alert(data);
            $('#modifyModal').modal('hide');

            const page = $('#todo-content').attr('data-pno');
            getTodoItemList(page);
        },
        error: function (request, status, error) {
            alert(request.responseText + "\n");
        }
    });
});

$('#searchTodoItem').on("keypress", function (e) {
    if (e.keyCode === 13) {
        const id = $(this).parents("div[data-id]").attr('data-id');
        const keyword = $(this).val();

        $('#searchList').html('');
        $.ajax({
            url: "/api/todos",
            type: "GET",
            data: {
                keyword: keyword,
                id: id
            },
            dataType: "json",
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
