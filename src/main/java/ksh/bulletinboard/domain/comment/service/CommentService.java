package ksh.bulletinboard.domain.comment.service;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.comment.service.dto.response.CommentServiceResponse;
import ksh.bulletinboard.domain.comment.repository.CommentRepository;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;


    public List<CommentServiceResponse> getCommentsOfPost(long id, boolean includeReply){
        List<Comment> comments = commentRepository.findByPostId(id);

        if(!includeReply){
            return comments.stream()
                    .map(CommentServiceResponse::from)
                    .toList();
        }

        List<Long> commentIds = comments.stream()
                .map(Comment::getId)
                .toList();

        Map<Comment, List<Reply>> map = groupingRepliesByComment(commentIds);

        return comments.stream()
                .map(comment
                        -> CommentServiceResponse.from(comment, map.getOrDefault(comment, List.of()))
                )
                .toList();

    }

    private Map<Comment, List<Reply>> groupingRepliesByComment(List<Long> commentIds) {
        return replyRepository.findByCommentIdIn(commentIds).stream()
                .collect(Collectors.groupingBy(Reply::getComment));
    }

}
