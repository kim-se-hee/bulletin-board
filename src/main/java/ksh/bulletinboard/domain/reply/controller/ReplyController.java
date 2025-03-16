package ksh.bulletinboard.domain.reply.controller;

import jakarta.validation.Valid;
import ksh.bulletinboard.domain.reply.controller.dto.request.ReplyCreationRequest;
import ksh.bulletinboard.domain.reply.controller.dto.response.ReplyListResponse;
import ksh.bulletinboard.domain.reply.controller.dto.response.ReplyResponse;
import ksh.bulletinboard.domain.reply.facade.ReplyApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyApplicationService replyApplicationService;

    @GetMapping("/boards/posts/comments/{commentId}/replies")
    public ResponseEntity<ReplyListResponse> allReplies(@PathVariable("commentId") long commentId) {
        List<ReplyResponse> replies = replyApplicationService.getRepliesOfComment(commentId)
                .stream()
                .map(ReplyResponse::from)
                .toList();
        ReplyListResponse response = ReplyListResponse.of(replies);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/boards/posts/comments/{commentId}/replies/new")
    public ResponseEntity<ReplyResponse> write(@Valid @RequestBody ReplyCreationRequest request) {
        ReplyResponse response = ReplyResponse.from(replyApplicationService.createReply(request.getContent(), request.getCommentId()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/boards/posts/comments/{commentId}/replies/{replyId}/edit")
    public ResponseEntity<ReplyResponse> edit(
            @PathVariable("replyId") Long id,
            @RequestBody String content
    ) {
        ReplyResponse response = ReplyResponse.from(replyApplicationService.editReply(id, content));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
