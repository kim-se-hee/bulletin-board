package ksh.bulletinboard.domain.comment.controller;

import ksh.bulletinboard.domain.comment.controller.dto.response.CommentResponse;
import ksh.bulletinboard.domain.comment.dto.response.CommentSerivceResponse;
import ksh.bulletinboard.domain.comment.repository.CommentRepository;
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

    @GetMapping("/board/{boardId}/comments")
    public ResponseEntity<CommentResponse> comments(
            @PathVariable("boardId") Long boardId,
            @RequestParam(value = "includeReply", required = false, defaultValue = "false") boolean includeReply
    ) {
        List<CommentSerivceResponse> comments;
        if(includeReply){
            comments = commentService.getCommentsWithRepliesOfPost(boardId);
        }else{
            comments = commentService.getCommentsOfPost(boardId);
        }
        CommentResponse response = CommentResponse.of(comments);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
