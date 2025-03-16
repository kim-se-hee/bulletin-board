package ksh.bulletinboard.domain.board.controller;

import ksh.bulletinboard.domain.board.controller.dto.BoardResponse;
import ksh.bulletinboard.domain.board.controller.dto.response.BoardListResponse;
import ksh.bulletinboard.domain.board.application.BoardApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardApplicationService boardApplicationService;

    @GetMapping("/boards")
    public ResponseEntity<BoardListResponse> boards() {
        List<BoardResponse> boards = boardApplicationService.getAllBoards()
                .stream()
                .map(BoardResponse::from)
                .toList();
        BoardListResponse response = BoardListResponse.of(boards);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
