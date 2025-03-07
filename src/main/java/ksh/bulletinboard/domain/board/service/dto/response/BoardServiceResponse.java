package ksh.bulletinboard.domain.board.service.dto.response;

import ksh.bulletinboard.domain.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardServiceResponse {

    private long id;
    private String title;

    public static BoardServiceResponse from(Board board) {
        return new BoardServiceResponse(board);
    }

    private BoardServiceResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
    }

}
