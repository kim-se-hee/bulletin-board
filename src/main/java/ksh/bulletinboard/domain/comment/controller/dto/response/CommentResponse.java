package ksh.bulletinboard.domain.comment.controller.dto.response;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.comment.dto.response.CommentSerivceResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CommentResponse {

    private List<CommentSerivceResponse> comments;

    public static CommentResponse of(List<CommentSerivceResponse> comments){
        return new CommentResponse(comments);
    }

    public CommentResponse(List<CommentSerivceResponse> comments) {
        this.comments = comments;
    }

}
