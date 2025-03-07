package ksh.bulletinboard.domain.post.service;

import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.board.repository.BoardRepository;
import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.member.repository.MemberRepository;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.repository.PostRepository;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

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

        PageRequest pageRequest = PageRequest.of(1, 2);

        //when
        PostPageResponse response = postService.getPostsOfBoard(board.getId(), pageRequest);

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

        PageRequest pageRequest = PageRequest.of(3, 2);

        //when
        PostPageResponse response = postService.getPostsOfBoard(board.getId(), pageRequest);

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

        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        PostPageResponse response = postService.getPostsOfBoardByTitle(board.getId(), "5", pageRequest);

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

        PageRequest pageRequest = PageRequest.of(0, 2);

        //when
        PostPageResponse response = postService.getPostsOfBoardByNickname(board.getId(), "회원1", pageRequest);

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

    @DisplayName("요청한 게시글을 조회하면 게시글의 정보가 반환되며 조회수가 증가한다")
    @Test
    void getSinglePost1() {
        //given
        Board board = createBoard();
        boardRepository.save(board);

        Post post = createPost("글", board);
        postRepository.save(post);

        //when
        PostResponse response = postService.getSinglePost(post.getId());

        //then
        assertThat(response)
                .extracting("title", "content", "views")
                .containsExactly("글", "본문", 2L);

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
