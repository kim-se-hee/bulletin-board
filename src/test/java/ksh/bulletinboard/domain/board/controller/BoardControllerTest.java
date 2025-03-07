package ksh.bulletinboard.domain.board.controller;

import ksh.bulletinboard.domain.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BoardController.class)
class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @DisplayName("모든 게시판을 조회할 수 있다")
    @Test
    void boards() throws Exception {
        //when //then
        mockMvc.perform(
                        get("/boards")
                ).andDo(print())
                .andExpect(status().isOk());
    }

}
