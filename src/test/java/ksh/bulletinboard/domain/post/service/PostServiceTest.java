package ksh.bulletinboard.domain.post.service;

import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.board.repository.BoardRepository;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.repository.PostRepository;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("요청한 게시판의 게시글 목록 페이지를 조회한다")
    @Test
    void getPostsOfBoard1(){
        //given
        Board board = Board.builder()
                .title("자유게시판")
                .build();
        boardRepository.save(board);

        Post post1 = Post.builder()
                .title("글1")
                .views(1L)
                .board(board)
                .build();

        Post post2 = Post.builder()
                .title("글2")
                .views(1L)
                .board(board)
                .build();

        Post post3 = Post.builder()
                .title("글3")
                .views(1L)
                .board(board)
                .build();
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
        Board board = Board.builder()
                .title("자유게시판")
                .build();
        boardRepository.save(board);

        Post post1 = Post.builder()
                .title("글1")
                .views(1L)
                .board(board)
                .build();

        Post post2 = Post.builder()
                .title("글2")
                .views(1L)
                .board(board)
                .build();

        Post post3 = Post.builder()
                .title("글3")
                .views(1L)
                .board(board)
                .build();
        postRepository.saveAll(List.of(post1, post2, post3));

        PageRequest pageRequest = PageRequest.of(3, 2);

        //when
        PostPageResponse response = postService.getPostsOfBoard(board.getId(), pageRequest);

        //then
        assertThat(response.getPostResponses()).isEmpty();

    }

}
