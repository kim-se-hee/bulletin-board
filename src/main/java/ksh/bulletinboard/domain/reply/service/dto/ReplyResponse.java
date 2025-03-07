package ksh.bulletinboard.domain.reply.service.dto;

import ksh.bulletinboard.domain.reply.domain.Reply;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyResponse {

    private long id;
    private String content;

    public static ReplyResponse from(Reply reply) {
        return new ReplyResponse(reply);
    }

    private ReplyResponse(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
    }

}
