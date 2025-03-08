package ksh.bulletinboard.domain.reply.controller;

import ksh.bulletinboard.domain.reply.service.ReplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReplyController.class)
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

}
