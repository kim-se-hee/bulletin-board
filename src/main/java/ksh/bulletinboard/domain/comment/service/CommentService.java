package ksh.bulletinboard.domain.comment.service;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.comment.dto.response.CommentSerivceResponse;
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

    public List<CommentSerivceResponse> getCommentsOfPost(long id){
        return commentRepository.findByPostId(id).stream()
                .map(CommentSerivceResponse::from)
                .toList();
    }

    public List<CommentSerivceResponse> getCommentsWithRepliesOfPost(long id){
        List<Comment> comments = commentRepository.findByPostId(id);

        List<Long> commentIds = comments.stream()
                .map(Comment::getId)
                .toList();

        Map<Comment, List<Reply>> map = replyRepository.findByCommentIdIn(commentIds).stream()
                .collect(Collectors.groupingBy(Reply::getComment));

        return comments.stream()
                .map(comment
                        -> CommentSerivceResponse.from(
                                comment,
                                map.getOrDefault(comment, List.of())
                        ))
                .toList();
    }

}
