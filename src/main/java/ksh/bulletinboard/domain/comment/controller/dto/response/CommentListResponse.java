package ksh.bulletinboard.domain.comment.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CommentListResponse {

    private List<CommentResponse> comments;

    public static CommentListResponse of(List<CommentResponse> comments){
        return new CommentListResponse(comments);
    }

    public CommentListResponse(List<CommentResponse> comments) {
        this.comments = comments;
    }

}
