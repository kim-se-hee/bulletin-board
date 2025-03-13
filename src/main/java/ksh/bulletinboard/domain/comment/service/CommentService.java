package ksh.bulletinboard.domain.comment.service;

import ksh.bulletinboard.domain.comment.domain.Comments;
import ksh.bulletinboard.domain.comment.repository.CommentRepository;
import ksh.bulletinboard.domain.comment.service.dto.response.CommentServiceResponse;
import ksh.bulletinboard.domain.reply.domain.Replies;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;


    public List<CommentServiceResponse> getCommentsOfPost(long id, boolean includeReply){
        Comments comments = Comments.of(commentRepository.findByPostId(id));

        if(!includeReply){
            return comments.toServiceResponses();
        }

        List<Long> commentIds = comments.getCommentIds();
        Map<Long, List<Reply>> map = Replies.of(replyRepository.findByCommentIdIn(commentIds))
                .groupingByCommentId();

        return comments.toServiceResponses(map);

    }

}
