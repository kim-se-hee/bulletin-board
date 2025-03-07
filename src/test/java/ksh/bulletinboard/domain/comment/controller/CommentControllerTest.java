package ksh.bulletinboard.domain.comment.controller;

import ksh.bulletinboard.domain.comment.domain.Comment;
import ksh.bulletinboard.domain.comment.dto.response.CommentSerivceResponse;
import ksh.bulletinboard.domain.comment.service.CommentService;
import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

    @DisplayName("게시글의 댓글을 조회한다")
    @Test
    void comments() throws Exception {
        //given
        List<CommentSerivceResponse> responses = List.of();
        given(commentService.getCommentsOfPost(anyLong()))
                .willReturn(responses);

        //when //then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/board/1/comments")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments").isArray());
     }

    @DisplayName("필요 시 게시글의 댓글과 함께 대댓글을 조회할 수 있다")
    @Test
    void commentsWithReplies() throws Exception {
        //when //then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/board/1/comments")
                                .param("includeReply", "true")
                ).andDo(print())
                .andExpect(status().isOk());
    }

}
