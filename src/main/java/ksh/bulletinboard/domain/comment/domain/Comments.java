package ksh.bulletinboard.domain.comment.domain;

import ksh.bulletinboard.domain.comment.service.dto.response.CommentServiceResponse;
import ksh.bulletinboard.domain.reply.domain.Reply;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Comments {

    private List<Comment> comments;

    public static Comments of(List<Comment> comments) {
        return new Comments(comments);
    }

    public List<CommentServiceResponse> toServiceResponses() {
        return comments.stream()
                .map(CommentServiceResponse::from)
                .toList();
    }

    public List<CommentServiceResponse> toServiceResponses(Map<Long, List<Reply>> replyMap) {
        return comments.stream()
                .map(comment
                        -> CommentServiceResponse.from(
                                comment, replyMap.getOrDefault(comment.getId(),  List.of()))
                )
                .toList();
    }

    public List<Long> getCommentIds() {
        return comments.stream()
                .map(Comment::getId)
                .collect(Collectors.toList());
    }

    private Comments(List<Comment> comments) {
        this.comments = comments;
    }



}
