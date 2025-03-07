package ksh.bulletinboard.domain.board.controller;

import ksh.bulletinboard.domain.board.controller.dto.response.BoardListResponse;
import ksh.bulletinboard.domain.board.service.BoardService;
import ksh.bulletinboard.domain.board.service.dto.response.BoardServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public ResponseEntity<BoardListResponse> boards() {
        List<BoardServiceResponse> boards = boardService.getAllBoards();
        BoardListResponse response = BoardListResponse.of(boards);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
