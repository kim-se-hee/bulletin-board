package ksh.bulletinboard.domain.comment.service;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.comment.dto.response.CommentResponse;
import ksh.bulletinboard.domain.comment.repository.CommentRepository;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.repository.PostRepository;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.repository.ReplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ReplyRepository replyRepository;

    @DisplayName("사용자가 요청한 게시글의 댓글을 조회한다")
    @Test
    void getCommentsOfPost(){
        //given
        Post post = Post.builder()
                .title("글")
                .build();
        postRepository.save(post);

        Comment comment1 = Comment.builder()
                .content("댓글1")
                .post(post)
                .build();

        Comment comment2 = Comment.builder()
                .content("댓글2")
                .post(post)
                .build();

        Comment comment3 = Comment.builder()
                .content("댓글3")
                .post(post)
                .build();

        commentRepository.saveAll(List.of(comment1, comment2, comment3));

        //when
        List<CommentResponse> commentsOfPost = commentService.getCommentsOfPost(post.getId());

        //then
        assertThat(commentsOfPost).hasSize(3)
                .extracting("content")
                .containsExactly("댓글1", "댓글2", "댓글3");

     }

     @DisplayName("사용자가 요청한 게시글의 모든 댓글과 대댓글을 조회한다")
     @Test
     void getCommentsWithRepliesOfPost(){
         //given
         Post post = Post.builder()
                 .title("글")
                 .build();
         postRepository.save(post);

         Comment comment1 = Comment.builder()
                 .content("댓글1")
                 .post(post)
                 .build();

         Comment comment2 = Comment.builder()
                 .content("댓글2")
                 .post(post)
                 .build();
         commentRepository.saveAll(List.of(comment1, comment2));

         Reply reply1 = Reply.builder()
                 .content("댓글1의 대댓글1")
                 .comment(comment1)
                 .build();

         Reply reply2 = Reply.builder()
                 .content("댓글1의 대댓글2")
                 .comment(comment1)
                 .build();

         Reply reply3 = Reply.builder()
                 .content("댓글1의 대댓글3")
                 .comment(comment1)
                 .build();
         replyRepository.saveAll(List.of(reply1, reply2, reply3));

         //when
         List<CommentResponse> response = commentService.getCommentsWithRepliesOfPost(post.getId());

         //then
         assertThat(response).hasSize(2)
                 .extracting("content")
                 .containsExactly("댓글1", "댓글2");

         assertThat(response.get(0).getReplies()).hasSize(3)
                 .extracting("content")
                 .containsExactly("댓글1의 대댓글1", "댓글1의 대댓글2","댓글1의 대댓글3");

         assertThat(response.get(1).getReplies()).isEmpty();

      }

}
