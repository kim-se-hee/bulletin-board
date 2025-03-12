package ksh.bulletinboard.domain.board.controller.dto;

import ksh.bulletinboard.domain.board.service.dto.response.BoardServiceResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardResponse {

    private long id;
    private String title;

    public static BoardResponse from(BoardServiceResponse response) {
        return new BoardResponse(response);
    }

    private BoardResponse(BoardServiceResponse response) {
        this.id = response.getId();
        this.title = response.getTitle();
    }

}
