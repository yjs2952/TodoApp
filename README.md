# TodoApp
### 개요
스프링부트와 타임리프로 제작한 Todo list 웹 어플리케이션

### 개발 환경
- JDK 1.8
- 스프링부트 2
- H2 DB
- gradle
- intelliJ
### 빌드 방법
- git pull https://github.com/yjs2952/TodoApp
- H2 DB 설치 http://www.h2database.com/html/main.html
- intelliJ 로 project import -> gradle로 설정
- intelliJ에서 lombok plugin 설치
- Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors 
  - Enable annotation processing 체크
- Settings -> Languages & Frameworks
  -> Javascript language version -> ECMAScript 6 설정
- 자동 build
- 프로젝트 실행  
- localhost:8080 접속
### 사용방법
#### 추가
- input box 에서 Todo 명을 입력하고 Enter 혹은 Add Todo 버튼 클릭
#### 수정
- 우측 수정 아이콘 클릭 시 모달 생성
- todo 명 수정
- 참조할 Todo 조회에서 Todo명 입력 후 Enter 클릭
  - 수정하는 Todo 자기자신과 현재 참조중인 Todo를 제외한 목록 조회
  - 체크박스 체크 시 참조할 Todo 추가
-  참조 Todo 목록
    - 현재 참조중인 Todo 목록
    - 체크 시 참조 제거
- 수정 버튼 클릭
#### 완료
- 좌측 체크 박스 체크 시 완료 상태
    - 이미 참조 중인 Todo가 있고 그 Todo가 완료 상태가 아니라면 완료할 수 없음
- 체크 해제 시 미완료 상태     
#### 삭제
- 우측 삭제 아이콘 클릭
    - 이미 참조중인 Todo가 있다면 삭제할 수 없음

