package ksh.bulletinboard.domain.comment.controller;

import ksh.bulletinboard.domain.comment.controller.dto.response.CommentListResponse;
import ksh.bulletinboard.domain.comment.controller.dto.response.CommentResponse;
import ksh.bulletinboard.domain.comment.service.CommentService;
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

    private final CommentService commentService;

    @GetMapping("/boards/posts/{postId}/comments")
    public ResponseEntity<CommentListResponse> comments(
            @PathVariable("postId") Long postId,
            @RequestParam(value = "includeReply", required = false, defaultValue = "false") boolean includeReply
    ) {
        List<CommentResponse> comments = commentService.getCommentsOfPost(postId, includeReply)
                .stream()
                .map(CommentResponse::from)
                .toList();

        CommentListResponse response = CommentListResponse.of(comments);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
