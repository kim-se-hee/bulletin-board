package ksh.bulletinboard.domain.post.service;

import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.board.repository.BoardRepository;
import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.member.repository.MemberRepository;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.repository.PostRepository;
import ksh.bulletinboard.domain.post.service.dto.request.PostEditServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostPageServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostRegisterServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostRegisterServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("요청한 게시판의 게시글 목록 페이지를 조회한다")
    @Test
    void getPostsOfBoard1(){
        //given
        Board board = createBoard();
        boardRepository.save(board);

        Post post1 = createPost("글1", board);
        Post post2 = createPost("글2", board);
        Post post3 = createPost("글3", board);
        postRepository.saveAll(List.of(post1, post2, post3));

        PostPageServiceRequest request = new PostPageServiceRequest(null, null, 1, 2);

        //when
        PostPageServiceResponse response = postService.getPostsOfBoard(board.getId(), request);

        //then
        assertThat(response)
                .extracting("pageNum", "pageSize",  "totalPages", "hasNext")
                .containsExactly(1, 2, 2, false);

        assertThat(response.getPostResponses()).hasSize(1)
                .extracting("title", "views")
                .containsExactly(tuple("글3", 1L));

     }

    @DisplayName("마지막 페이지 번호보다 큰 페이지 번호가 주어지면 빈 리스트가 반환된다")
    @Test
    void getPostsOfBoard2(){
        //given
        Board board = createBoard();
        boardRepository.save(board);

        Post post1 = createPost("글1", board);
        Post post2 = createPost("글2", board);
        Post post3 = createPost("글3", board);
        postRepository.saveAll(List.of(post1, post2, post3));

        PostPageServiceRequest request = new PostPageServiceRequest(null, null, 3, 2);

        //when
        PostPageServiceResponse response = postService.getPostsOfBoard(board.getId(), request);

        //then
        assertThat(response.getPostResponses()).isEmpty();

    }

    @DisplayName("요청한 게시판의 게시글을 제목으로 검색 후 게시글의 목록을 페이지로 조회한다")
    @Test
    void getPostsOfBoardAndTitle1() {
        //given
        Board board = createBoard();
        boardRepository.save(board);

        Post post1 = createPost("글123", board);
        Post post2 = createPost("글456", board);
        Post post3 = createPost("글789", board);
        postRepository.saveAll(List.of(post1, post2, post3));

        PostPageServiceRequest request = new PostPageServiceRequest("5", null, 0, 2);

        //when
        PostPageServiceResponse response = postService.getPostsOfBoard(board.getId(), request);

        //then
        assertThat(response)
                .extracting("pageNum", "pageSize", "totalPages", "hasNext")
                .containsExactly(0, 2, 1, false);

        assertThat(response.getPostResponses()).hasSize(1)
                .extracting("title", "views")
                .containsExactly(tuple("글456", 1L));

    }

    @DisplayName("요청한 게시판의 게시글을 작성자로 검색 후 게시글의 목록을 페이지로 조회한다")
    @Test
    void getPostsOfBoardAndMemberNickname() {
        //given
        Board board = createBoard();
        boardRepository.save(board);

        Member member1 = createMember("회원1");
        Member member2 = createMember("회원2");
        memberRepository.saveAll(List.of(member1, member2));

        Post post1 = createPost("글123", board, member1);
        Post post2 = createPost("글456", board, member1);
        Post post3 = createPost("글789", board, member2);
        postRepository.saveAll(List.of(post1, post2, post3));

        PostPageServiceRequest request = new PostPageServiceRequest(null, "회원1", 0, 2);

        //when
        PostPageServiceResponse response = postService.getPostsOfBoard(board.getId(), request);

        //then
        assertThat(response)
                .extracting("pageNum", "pageSize", "totalPages", "hasNext")
                .containsExactly(0, 2, 1, false);

        assertThat(response.getPostResponses()).hasSize(2)
                .extracting("title", "views")
                .containsExactly(
                        tuple("글123", 1L),
                        tuple("글456", 1L)
                );

    }

    @DisplayName("요청한 게시글을 조회하면 게시글의 정보가 반환된다")
    @Test
    void getSinglePost1() {
        //given
        Board board = createBoard();
        boardRepository.save(board);

        Member member = createMember("member1");
        memberRepository.save(member);

        Post post = createPost("글", board, member);
        postRepository.save(post);

        //when
        PostServiceResponse response = postService.getSinglePost(post.getId());

        //then
        assertThat(response)
                .extracting("title", "content")
                .containsExactly("글", "본문");

    }

    @DisplayName("존재하지 않는 게시글을 요청하면 예외가 발생한다")
    @Test
    void getSinglePost2() {
        //given
        Board board = createBoard();
        boardRepository.save(board);

        Post post = createPost("글", board);
        postRepository.save(post);

        //when //then
        assertThatThrownBy(() -> postService.getSinglePost(100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 글입니다");
    }

    @DisplayName("회원이 게시판에 글을 작성한다")
    @Test
    void writePost1() throws IOException {
        //given
        Board board = createBoard();
        boardRepository.save(board);

        Member member = createMember("member1");
        memberRepository.save(member);

        PostRegisterServiceRequest request = new PostRegisterServiceRequest("제목", "내용", List.of(), board.getId(), member.getId());

        //when
        PostRegisterServiceResponse response = postService.writePost(request);

        //then
        assertThat(response.getId()).isGreaterThan(0);
        assertThat(response.getTitle()).isEqualTo("제목");

    }

    @DisplayName("가입하지 않은 회원이 글을 쓰려하면 예외가 발생한다")
    @Test
    void writePost2(){
        //given
        Board board = createBoard();
        boardRepository.save(board);

        PostRegisterServiceRequest request = new PostRegisterServiceRequest("제목", "내용", List.of(), 1L, board.getId());

        //when //then
        assertThatThrownBy(() -> postService.writePost(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 사용자입니다");

    }

    @DisplayName("존재하지 않는 게시판에 글을 쓰려하면 예외가 발생한다")
    @Test
    void writePost3(){
        //given
        Member member = createMember("member1");
        memberRepository.save(member);

        PostRegisterServiceRequest request = new PostRegisterServiceRequest("제목", "내용", List.of(), 1L, member.getId());

        //when //then
        assertThatThrownBy(() -> postService.writePost(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 게시판입니다");

    }

    @DisplayName("글의 제목 또는 내용을 수정한다")
    @Test
    void editPost1(){
        //given
        Member member = createMember("member1");
        memberRepository.save(member);

        Post post = Post.builder()
                .title("title1")
                .content("content1")
                .member(member)
                .views(1L)
                .build();
        postRepository.save(post);

        PostEditServiceRequest request = new PostEditServiceRequest(post.getId(), "title2", "content2");

        //when
        PostServiceResponse response = postService.editPost(request);

        //then
        assertThat(response.getId()).isEqualTo(post.getId());
        assertThat(response.getTitle()).isEqualTo("title2");
        assertThat(response.getContent()).isEqualTo("content2");

    }

    @DisplayName("존재하지 않는 글을 수정하려 하면 예외가 발생한다")
    @Test
    void editWithException(){
        //given
        PostEditServiceRequest request = new PostEditServiceRequest(1L, "title2", "content2");

        //when //then
        assertThatThrownBy(() -> postService.editPost(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 게시글입니다");

    }

    private static Board createBoard() {
        return Board.builder()
                .title("자유게시판")
                .build();
    }

    private static Member createMember(String member1) {
        return Member.builder()
                .nickname(member1)
                .build();
    }

    private static Post createPost(String title, Board board) {
        return Post.builder()
                .title(title)
                .content("본문")
                .views(1L)
                .board(board)
                .build();
    }

    private static Post createPost(String title, Board board, Member member) {
        return Post.builder()
                .title(title)
                .content("본문")
                .views(1L)
                .board(board)
                .member(member)
                .build();
    }
    
}
