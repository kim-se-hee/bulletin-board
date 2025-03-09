package ksh.bulletinboard.global.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ksh.bulletinboard.domain.member.service.MemberService;
import ksh.bulletinboard.global.login.controller.dto.request.LoginInfoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @DisplayName("아이디와 비밀번호로 로그인을 한다")
    @Test
    void login() throws Exception {
        //given
        String nickname = "nickname";
        String password = "password";
        LoginInfoRequest request = new LoginInfoRequest(nickname, password);

        given(memberService.getLoginMemberId(anyString(), anyString())).willReturn(1L);

        //when //then
        mockMvc.perform(
                        post("/login")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute("memberId", 1L));

    }

    @DisplayName("로그아웃을 한다")
    @Test
    void logout() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", 1L);

        //when //then
        MvcResult result = mockMvc.perform(
                        post("/logout")
                                .session(session)
                ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getRequest().getSession(false)).isNull();
    }

}
