package ksh.bulletinboard.domain.reply.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ksh.bulletinboard.domain.reply.controller.dto.request.ReplyCreationRequest;
import ksh.bulletinboard.domain.reply.service.ReplyService;
import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyController.class)
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReplyService replyService;

    @DisplayName("댓글의 대댓글을 조회한다")
    @Test
    void allReplies() throws Exception {
        //when //then
        mockMvc.perform(
                        get("/boards/posts/comments/1/replies")
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName("대댓글을 작성한다")
    @Test
    void createReply() throws Exception {
        //given
        ReplyCreationRequest request = new ReplyCreationRequest("내용", 1L);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", 1L);

        ReplyServiceResponse serviceResponse = new ReplyServiceResponse();
        BDDMockito.given(replyService.createReply(anyString(), anyLong()))
                .willReturn(serviceResponse);

        //when //then
        mockMvc.perform(
                        post("/boards/posts/comments/1/replies/new")
                                .session(session)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated());

    }

    @DisplayName("대댓글을 수정한다")
    @Test
    void editReply() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", 1L);

        ReplyServiceResponse serviceResponse = new ReplyServiceResponse();
        BDDMockito.given(replyService.editReply(anyLong(), anyString()))
                        .willReturn(serviceResponse);

        //when //then
        mockMvc.perform(
                        post("/boards/posts/comments/1/replies/1/edit")
                                .session(session)
                                .content("{\"content\":\"내용\"}")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());

    }

}
