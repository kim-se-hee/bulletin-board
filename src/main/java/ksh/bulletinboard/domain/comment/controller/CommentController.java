package ksh.bulletinboard.domain.comment.controller;

import ksh.bulletinboard.domain.comment.controller.dto.response.CommentListResponse;
import ksh.bulletinboard.domain.comment.controller.dto.response.CommentResponse;
import ksh.bulletinboard.domain.comment.service.dto.response.CommentServiceResponse;
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
        List<CommentServiceResponse> comments;
        if(includeReply){
            comments = commentService.getCommentsWithRepliesOfPost(postId);
        }else{
            comments = commentService.getCommentsOfPost(postId);
        }

        List<CommentResponse> commentList = comments.stream()
                .map(CommentResponse::from)
                .toList();
        CommentListResponse response = CommentListResponse.of(commentList);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
