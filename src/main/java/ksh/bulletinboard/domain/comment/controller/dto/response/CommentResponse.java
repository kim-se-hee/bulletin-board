package ksh.bulletinboard.domain.comment.controller.dto.response;

import ksh.bulletinboard.domain.comment.service.dto.response.CommentServiceResponse;
import ksh.bulletinboard.domain.reply.controller.dto.response.ReplyResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponse {

    long id;
    String content;
    List<ReplyResponse> replies;

    public static CommentResponse from(CommentServiceResponse response) {
        return new CommentResponse(response);
    }

    private CommentResponse(CommentServiceResponse response) {
        this.id = response.getId();
        this.content = response.getContent();
        this.replies = response.getReplies()
                .stream()
                .map(ReplyResponse::from)
                .toList();
    }

}
