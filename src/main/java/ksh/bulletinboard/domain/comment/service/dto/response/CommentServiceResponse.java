package ksh.bulletinboard.domain.comment.service.dto.response;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentServiceResponse {

    long id;
    String content;
    List<ReplyServiceResponse> replies;

    public static CommentServiceResponse from(Comment comment) {
        return new CommentServiceResponse(comment);
    }

    public static CommentServiceResponse from(Comment comment, List<Reply> replies) {
        return new CommentServiceResponse(comment, replies);
    }

    private CommentServiceResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.replies = new ArrayList<>();
    }

    private CommentServiceResponse(Comment comment, List<Reply> replies) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.replies = replies.stream()
                .map(ReplyServiceResponse::from)
                .toList();
    }

}
