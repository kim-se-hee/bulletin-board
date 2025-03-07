package ksh.bulletinboard.domain.reply.controller.dto.response;

import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReplyResponse {

    private List<ReplyServiceResponse> replies;

    public static ReplyResponse of(List<ReplyServiceResponse> replyDtos) {
        return new ReplyResponse(replyDtos);
    }

    private ReplyResponse(List<ReplyServiceResponse> replyDtos) {
        this.replies = replyDtos;
    }

}
