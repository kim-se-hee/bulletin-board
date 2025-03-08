package ksh.bulletinboard.domain.comment.controller;

import ksh.bulletinboard.domain.comment.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @DisplayName("게시글의 댓글을 조회한다")
    @Test
    void comments() throws Exception {
        //when //then
        mockMvc.perform(
                    get("/post/1/comments")
                ).andDo(print())
                .andExpect(status().isOk());
     }

    @DisplayName("필요 시 게시글의 댓글과 함께 대댓글을 조회할 수 있다")
    @Test
    void commentsWithReplies() throws Exception {
        //when //then
        mockMvc.perform(
                        get("/post/1/comments")
                                .param("includeReply", "true")
                ).andDo(print())
                .andExpect(status().isOk());
    }

}
