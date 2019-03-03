# TodoApp
### 개요
Spring Boot와 JPA를 이용한 Todo list 웹 어플리케이션

### 개발 환경
- JDK 1.8
- Spring Boot 2
- H2 DB
- gradle
- intelliJ
### 빌드 방법
- git pull https://github.com/yjs2952/TodoApp
- intelliJ 로 project import -> gradle 로 설정
- intelliJ에서 lombok plugin 설치
- Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors 
  - Enable annotation processing 체크
- 자동 build
- 프로젝트 실행  
- localhost:8080 접속
### 사용방법
#### 추가
- input box 에서 TodoItem 명을 입력하고 Enter 혹은 Add Todo 버튼 클릭
#### 수정
- 우측 수정 아이콘 클릭 시 모달 생성
- TodoItem 명 수정
- 참조할 TodoItem 조회에서 TodoItem 명 입력 후 Enter 클릭
  - 수정하는 Todo 자기자신과 현재 참조중인 TodoItem 을 제외한 목록 조회
  - 체크박스 체크 시 참조할 TodoItem 추가
-  참조 TodoItem 목록
    - 현재 참조중인 TodoItem 목록
    - 체크 시 참조 제거
- 수정 버튼 클릭
#### 완료
- 좌측 체크 박스 체크 시 완료 상태
    - 이미 참조 중인 TodoItem 이 있고 그 TodoItem 이 완료 상태가 아니라면 완료할 수 없음
- 체크 해제 시 미완료 상태     
#### 삭제
- 우측 삭제 아이콘 클릭
    - 완료되지 않은 참조중인 TodoItem 이 있다면 삭제할 수 없음

