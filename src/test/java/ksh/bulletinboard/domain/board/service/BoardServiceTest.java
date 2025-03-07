package ksh.bulletinboard.domain.board.service;

import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.board.repository.BoardRepository;
import ksh.bulletinboard.domain.board.service.dto.response.BoardResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("존재하는 게시판을 모두 조회한다")
    @Test
    void getAllBoards(){
        //given
        Board board1 = Board.builder()
                .title("게시판1")
                .build();

        Board board2 = Board.builder()
                .title("게시판2")
                .build();

        Board board3 = Board.builder()
                .title("게시판3")
                .build();
        boardRepository.saveAll(List.of(board1, board2, board3));

        //when
        List<BoardResponse> boards = boardService.getAllBoards();

        //then
        assertThat(boards).hasSize(3)
                .extracting("title")
                .containsExactlyInAnyOrder("게시판1", "게시판2", "게시판3");

     }

}
