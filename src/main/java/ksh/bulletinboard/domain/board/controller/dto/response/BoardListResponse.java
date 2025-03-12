package ksh.bulletinboard.domain.board.controller.dto.response;

import ksh.bulletinboard.domain.board.controller.dto.BoardResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardListResponse {

    private List<BoardResponse> boards;

    public static BoardListResponse of(List<BoardResponse> boards) {
        return new BoardListResponse(boards);
    }

    private BoardListResponse(List<BoardResponse> boards) {
        this.boards = boards;
    }
}
