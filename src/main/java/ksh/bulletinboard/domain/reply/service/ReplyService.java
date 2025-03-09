package ksh.bulletinboard.domain.reply.service;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.comment.repository.CommentRepository;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
import ksh.bulletinboard.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    public List<ReplyServiceResponse> getRepliesOfComment(long id){
        return replyRepository.findByCommentId(id).stream()
                .map(ReplyServiceResponse::from)
                .toList();
    }

    @Transactional
    public ReplyServiceResponse createReply(String content, long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다"));

        Reply reply = Reply.builder()
                .content(content)
                .comment(comment)
                .build();
        replyRepository.save(reply);

        return ReplyServiceResponse.from(reply);
    }

    @Transactional
    public ReplyServiceResponse editReply(long id, String content){
        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대댓글입니다"));

        reply.editContent(content);
        return ReplyServiceResponse.from(reply);
    }

}
