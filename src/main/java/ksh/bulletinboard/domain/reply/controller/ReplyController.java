package ksh.bulletinboard.domain.reply.controller;

import jakarta.validation.Valid;
import ksh.bulletinboard.domain.reply.controller.dto.request.ReplyCreationRequest;
import ksh.bulletinboard.domain.reply.controller.dto.response.ReplyResponse;
import ksh.bulletinboard.domain.reply.service.ReplyService;
import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/post/{postId}/reply")
    public ResponseEntity<ReplyResponse> allReplies(@PathVariable("postId") long postId) {
        List<ReplyServiceResponse> replies = replyService.getRepliesOfComment(postId);
        ReplyResponse response = ReplyResponse.of(replies);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/reply")
    public ResponseEntity<ReplyServiceResponse> write(@Valid @RequestBody ReplyCreationRequest request) {
        ReplyServiceResponse response = replyService.createReply(request.getContent(), request.getCommentId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

}
