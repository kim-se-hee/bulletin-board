package ksh.bulletinboard.domain.board.controller.dto.response;

import ksh.bulletinboard.domain.board.service.dto.response.BoardServiceResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BoardListResponse {

    private List<BoardServiceResponse> boards;

    public static BoardListResponse of(List<BoardServiceResponse> boards) {
        return new BoardListResponse(boards);
    }

    private BoardListResponse(List<BoardServiceResponse> boards) {
        this.boards = boards;
    }
}
