package ksh.bulletinboard.domain.reply;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.comment.repository.CommentRepository;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.dto.ReplyResponse;
import ksh.bulletinboard.domain.reply.repository.ReplyRepository;
import ksh.bulletinboard.domain.reply.service.ReplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplyServiceTest {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("사용자가 요청한 댓글의 대댓글을 조회한다")
    @Test
    void getRepliesOfComment(){
        //given
        Comment comment = Comment.builder()
                .content("댓글")
                .build();
        commentRepository.save(comment);

        Reply reply1 = Reply.builder()
                .content("대댓글1")
                .comment(comment)
                .build();
        replyRepository.save(reply1);

        Reply reply2 = Reply.builder()
                .content("대댓글2")
                .comment(comment)
                .build();
        replyRepository.saveAll(List.of(reply1,reply2));

        //when
        List<ReplyResponse> repliesOfComment = replyService.getRepliesOfComment(comment.getId());

        //then
        assertThat(repliesOfComment).hasSize(2)
                .extracting("content")
                .containsExactly("대댓글1", "대댓글2");

     }

}
