package ksh.bulletinboard.domain.comment.dto.response;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.dto.ReplyResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponse {

    long id;
    String content;
    List<ReplyResponse> replies;

    private CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.replies = new ArrayList<>();
    }

    private CommentResponse(Comment comment, List<Reply> replies) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.replies = replies.stream()
                .map(ReplyResponse::from)
                .toList();
    }

    public static CommentResponse from(Comment comment) {
        return new  CommentResponse(comment);
    }

    public static CommentResponse from(Comment comment, List<Reply> replies) {
        return new  CommentResponse(comment, replies);
    }

}
