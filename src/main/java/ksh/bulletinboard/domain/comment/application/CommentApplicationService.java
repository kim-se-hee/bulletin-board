package ksh.bulletinboard.domain.comment.application;

import ksh.bulletinboard.domain.comment.domain.Comments;
import ksh.bulletinboard.domain.comment.service.CommentService;
import ksh.bulletinboard.domain.comment.service.dto.response.CommentServiceResponse;
import ksh.bulletinboard.domain.reply.domain.Replies;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.service.ReplyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Getter
@RequiredArgsConstructor
public class CommentApplicationService {

    private final CommentService commentService;
    private final ReplyService replyService;

    public List<CommentServiceResponse> getCommentsOfPost(long id, boolean includeReply){
        Comments comments = Comments.of(commentService.getByPostId(id));
        if(includeReply){
            Map<Long, List<Reply>> commentMap = Replies.of(replyService.getRepliesOfCommentsIn(comments.getCommentIds()))
                    .groupingByCommentId();
            return comments.toServiceResponses(commentMap);
        }

        return comments.toServiceResponses();
    }

}
