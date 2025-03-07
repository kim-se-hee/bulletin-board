package ksh.bulletinboard.domain.reply.service.dto;

import ksh.bulletinboard.domain.reply.domain.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyServiceResponse {

    private long id;
    private String content;

    public static ReplyServiceResponse from(Reply reply) {
        return new ReplyServiceResponse(reply);
    }

    private ReplyServiceResponse(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
    }

}
