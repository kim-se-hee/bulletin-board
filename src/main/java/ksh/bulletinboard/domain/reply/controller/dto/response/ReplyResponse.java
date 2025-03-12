package ksh.bulletinboard.domain.reply.controller.dto.response;

import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyResponse {

    private long id;
    private String content;

    public static ReplyResponse from(ReplyServiceResponse response) {
        return new ReplyResponse(response);
    }

    private ReplyResponse(ReplyServiceResponse response) {
        this.id = response.getId();
        this.content = response.getContent();
    }
}
