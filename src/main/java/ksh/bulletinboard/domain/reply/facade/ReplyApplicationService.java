package ksh.bulletinboard.domain.reply.facade;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.comment.service.CommentService;
import ksh.bulletinboard.domain.reply.domain.Replies;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.service.ReplyService;
import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
public class ReplyApplicationService {

    private final ReplyService replyService;
    private final CommentService commentService;

    public List<ReplyServiceResponse> getRepliesOfComment(long id){
        Replies replies = Replies.of(replyService.getRepliesOfComment(id));
        return replies.toServiceResponse();
    }

    @Transactional
    public ReplyServiceResponse createReply(String content, long commentId){
        Comment comment = commentService.getById(commentId);
        Reply reply = replyService.createReply(content, comment);
        return ReplyServiceResponse.from(reply);
    }

    @Transactional
    public ReplyServiceResponse editReply(long id, String content){
        Reply reply = replyService.editReply(id, content);
        return ReplyServiceResponse.from(reply);
    }

}
