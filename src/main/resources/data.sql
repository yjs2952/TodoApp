INSERT INTO todo_item (content, is_checked, status, reg_date, mod_date) VALUES ('자바 스터디', 1, 'DONE', now(), now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('스프링 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('DB 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('스프링부트 스터디', 0, 'REF', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('자바스크립트 스터디', 0, 'REF', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('리액트 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('뷰 스터디', 1, 'DONE', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('스프링 시큐리티 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('안드로이드 스터디', 1, 'DONE', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('리액트 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('뷰 스터디', 1, 'DONE', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('스프링 시큐리티 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('안드로이드 스터디', 1, 'DONE', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('리액트 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('뷰 스터디', 1, 'DONE', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('스프링 시큐리티 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('안드로이드 스터디', 1, 'DONE', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('리액트 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('뷰 스터디', 1, 'DONE', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('스프링 시큐리티 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('안드로이드 스터디', 1, 'DONE', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('리액트 스터디', 0, 'TODO', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('뷰 스터디', 0, 'REF', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('스프링 시큐리티 스터디', 0, 'REF', now());
INSERT INTO todo_item (content, is_checked, status, reg_date) VALUES ('안드로이드 스터디', 1, 'REF', now());



INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (1, 23);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (2, 23);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (6, 23);

INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (2, 4);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (3, 4);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (5, 4);

INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (2, 5);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (3, 5);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (4, 5);

INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (1, 24);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (2, 24);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (3, 24);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (5, 24);

INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (1, 22);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (2, 22);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (3, 22);
INSERT INTO todo_reference (prev_todo_id, current_todo_id) VALUES (5, 22);