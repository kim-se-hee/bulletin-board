package ksh.bulletinboard.domain.comment.controller.dto.response;

import ksh.bulletinboard.domain.comment.service.dto.response.CommentServiceResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CommentResponses {

    private List<CommentResponse> comments;

    public static CommentResponses from(List<CommentServiceResponse> serviceResponses) {
        return new CommentResponses(serviceResponses);
    }

    private CommentResponses(List<CommentServiceResponse> serviceResponses) {
        this.comments = serviceResponses.stream()
                .map(CommentResponse::from)
                .toList();
    }

}
