# 게시판

---

## DB 설계
![Image](https://github.com/user-attachments/assets/02b699a2-7041-46e8-be8f-9d464550a0d0)

### board (게시판)
- title :게시판 명
### post (게시글)
- title : 제목
- cotent : 본문 (길지 않은 텍스트로 이뤄졌다 가정)
- views : 조회수
- board 와 post는 1 : N 관계
### comment (댓글)
- content : 댓글 내용
- post 와 comment는 1 : N 관계
### reply (대댓글)
- content : 대댓글 내용
- comment 와 reply는 1 : N 관계
### member (회원)
- nickname : 닉네임 겸 로그인 시 사용하는 아이디
- password : 비밀번호
- name : 실명
- member 와 post는 1 : N 관계
---
## API
### board 도메인
- GET /boards : 모든 게시판 조회
### post 도메인
- GET /board/{boardId}/post/{postId} : 게시판의 특정 글을 단건 조회
- GET /board/{boardId}/posts : 게시판의 글 목록을 조회
- POST /post : 새로운 글을 작성
- POST /post/{postId} : 요청한 글의 제목 또는 내용을 수정
- POST /post/{postId}/views : 게시글의 조회수를 증가
### comment 도메인
- GET /post/{postId}/comments : 게시물의 댓글 조회
### reply 도메인
- GET /post/{postId}/reply : 댓글의 대댓글을 조회
- POST /reply : 대댓글을 작성
- POST /reply/{replyId} : 대댓글을 수정
### member 도메인
- POST /member : 회원 가입
---
## 커밋 메세지 규칙
- feat: 기능 추가
- fix: 버그 수정
- docs: 문서 수정
- style: 코드 포맷 변경
- refactor: 프로덕션 코드 리팩터링
- test: 테스트 관련 작업
- chore: 프로젝트 설정 관련 작업
