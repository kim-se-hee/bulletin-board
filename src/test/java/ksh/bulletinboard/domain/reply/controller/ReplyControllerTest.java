package ksh.bulletinboard.domain.reply.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ksh.bulletinboard.domain.reply.controller.dto.request.ReplyCreationRequest;
import ksh.bulletinboard.domain.reply.service.ReplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

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
                        get("/post/1/reply")
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

        //when //then
        mockMvc.perform(
                        post("/reply")
                                .session(session)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated());

    }

}
