package ksh.bulletinboard.domain.reply;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.comment.repository.CommentRepository;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
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
        List<ReplyServiceResponse> repliesOfComment = replyService.getRepliesOfComment(comment.getId());

        //then
        assertThat(repliesOfComment).hasSize(2)
                .extracting("content")
                .containsExactly("대댓글1", "대댓글2");

     }

     @DisplayName("댓글에 대한 대댓글을 작성한다")
     @Test
     void createReply(){
         //given
         Comment comment = Comment.builder()
                 .content("댓글")
                 .build();
         commentRepository.save(comment);

         //when
         ReplyServiceResponse response = replyService.createReply("대댓글", comment.getId());

         //then
         assertThat(response.getId()).isGreaterThan(0);
         assertThat(response.getContent()).isEqualTo("대댓글");

      }

    @DisplayName("존재하지 않는 댓글에 대한 대댓글을 작성하려 하면 예외가 발생한다")
    @Test
    void createReplyWithException(){
        //when //then
        assertThatThrownBy(() -> replyService.createReply("대댓글", 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 댓글입니다");
    }

    @DisplayName("작성한 대댓글을 수정한다")
    @Test
    void editReply(){
        //given
        Reply reply = Reply.builder()
                .content("수정 전")
                .build();
        replyRepository.save(reply);

        //when
        ReplyServiceResponse response = replyService.editReply(reply.getId(), "수정 후");

        //then
        assertThat(response.getId()).isEqualTo(reply.getId());
        assertThat(response.getContent()).isEqualTo("수정 후");

    }

    @DisplayName("존재하지 않는 댓글에 대한 대댓글을 수정하려 하면 예외가 발생한다")
    @Test
    void editReplyWithException(){
        //when //then
        assertThatThrownBy(() -> replyService.editReply(10L, "내용"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 대댓글입니다");
    }

}
