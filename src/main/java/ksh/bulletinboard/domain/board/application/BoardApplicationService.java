package ksh.bulletinboard.domain.board.application;

import ksh.bulletinboard.domain.board.service.BoardService;
import ksh.bulletinboard.domain.board.service.dto.response.BoardServiceResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
public class BoardApplicationService {

    private final BoardService boardService;

    public List<BoardServiceResponse> getAllBoards() {
        return boardService.getAllBoards().stream()
                .map(BoardServiceResponse::from)
                .toList();
    }

}
