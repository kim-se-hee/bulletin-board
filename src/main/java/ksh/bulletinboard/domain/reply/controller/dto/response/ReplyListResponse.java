package ksh.bulletinboard.domain.reply.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReplyListResponse {

    private List<ReplyResponse> replies;

    public static ReplyListResponse of(List<ReplyResponse> replyDtos) {
        return new ReplyListResponse(replyDtos);
    }

    private ReplyListResponse(List<ReplyResponse> replyDtos) {
        this.replies = replyDtos;
    }

}
