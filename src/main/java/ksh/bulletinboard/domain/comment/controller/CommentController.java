package ksh.bulletinboard.domain.comment.controller;

import ksh.bulletinboard.domain.comment.controller.dto.response.CommentResponses;
import ksh.bulletinboard.domain.comment.application.CommentApplicationService;
import ksh.bulletinboard.domain.comment.service.dto.response.CommentServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentApplicationService commentFacade;

    @GetMapping("/boards/posts/{postId}/comments")
    public ResponseEntity<CommentResponses> comments(
            @PathVariable("postId") Long postId,
            @RequestParam(value = "includeReply", required = false, defaultValue = "false") boolean includeReply
    ) {
        List<CommentServiceResponse> comments = commentFacade.getCommentsOfPost(postId, includeReply);

        CommentResponses response = CommentResponses.from(comments);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
