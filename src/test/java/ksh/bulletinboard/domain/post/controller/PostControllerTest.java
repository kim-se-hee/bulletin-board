package ksh.bulletinboard.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ksh.bulletinboard.domain.post.controller.dto.request.PostRegisterRequest;
import ksh.bulletinboard.domain.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @DisplayName("게시글을 조회한다")
    @Test
    void showPost() throws Exception {
        //when //then
        mockMvc.perform(
                        get("/board/1/post/1")
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName("게시글 목록을 조회할 때 제목으로 필터링을 할 수 있다")
    @Test
    void postsWithTitle() throws Exception {
        //when //then
        mockMvc.perform(
                        get("/board/1/posts")
                                .queryParam("title", "글")
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 목록을 조회할 때 작성자 닉네임으로 필터링을 할 수 있다")
    @Test
    void postsWithNickname() throws Exception {
        //when //then
        mockMvc.perform(
                        get("/board/1/posts")
                                .queryParam("nickname", "작성자")
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 목록을 조회할 때 페이징 정보를 지정할 수 있다")
    @Test
    void postsWithPaging() throws Exception {
        //when //then
        mockMvc.perform(
                        get("/board/1/posts")
                                .queryParam("pageNum", "0")
                                .queryParam("pageSize", "10")
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("게시글을 작성한다")
    @Test
    void write() throws Exception {
        //given
        PostRegisterRequest request = new PostRegisterRequest("제목", "내용", 1L, 1L);

        //when //then
        mockMvc.perform(
                        post("/post")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

}
