package ksh.bulletinboard.domain.board.service.dto.response;

import ksh.bulletinboard.domain.board.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponse {

    private long id;
    private String title;

    public static BoardResponse from(Board board) {
        return new  BoardResponse(board);
    }

    private BoardResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
    }

}
