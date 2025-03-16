package ksh.bulletinboard.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ksh.bulletinboard.domain.post.application.PostApplicationService;
import ksh.bulletinboard.domain.post.service.dto.request.PostEditServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostPageServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostRegisterServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostApplicationService postApplicationService;

    @DisplayName("게시글을 조회한다")
    @Test
    void showPost() throws Exception {
        //given
        PostServiceResponse serviceResponse = new PostServiceResponse();
        given(postApplicationService.getSinglePost(anyLong()))
                .willReturn(serviceResponse);

        //when //then
        mockMvc.perform(
                        get("/boards/1/posts/1")
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName("게시글 목록을 조회할 때 제목으로 필터링을 할 수 있다")
    @Test
    void postsWithTitle() throws Exception {
        //given
        PostPageServiceResponse serviceResponse = new PostPageServiceResponse();
        given(postApplicationService.getPostsOfBoard(anyLong(), any(PostPageServiceRequest.class)))
                .willReturn(serviceResponse);

        //when //then
        mockMvc.perform(
                        get("/boards/1/posts")
                                .queryParam("title", "글")
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 목록을 조회할 때 작성자 닉네임으로 필터링을 할 수 있다")
    @Test
    void postsWithNickname() throws Exception {
        //given
        PostPageServiceResponse serviceResponse = new PostPageServiceResponse();
        given(postApplicationService.getPostsOfBoard(anyLong(), any(PostPageServiceRequest.class)))
                .willReturn(serviceResponse);

        //when //then
        mockMvc.perform(
                        get("/boards/1/posts")
                                .queryParam("nickname", "작성자")
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 목록을 조회할 때 페이징 정보를 지정할 수 있다")
    @Test
    void postsWithPaging() throws Exception {
        //given
        PostPageServiceResponse serviceResponse = new PostPageServiceResponse();
        given(postApplicationService.getPostsOfBoard(anyLong(), any(PostPageServiceRequest.class)))
                .willReturn(serviceResponse);

        //when //then
        mockMvc.perform(
                        get("/boards/1/posts")
                                .queryParam("pageNum", "0")
                                .queryParam("pageSize", "10")
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("게시글을 작성한다")
    @Test
    void write() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", 1L);

        given(postApplicationService.createPost(any()))
                .willReturn(new PostRegisterServiceResponse());

        //when //then
        mockMvc.perform(
                        multipart("/boards/1/posts/new")
                                .file(new MockMultipartFile("files", new byte[0]))
                                .param("title", "제목")
                                .param("content", "내용")
                                .session(session)
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("게시글을 수정한다")
    @Test
    void edit() throws Exception {
        //given
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("memberId", 1L);
        PostEditServiceRequest request = new PostEditServiceRequest(1L, "title2", "content2");

        given(postApplicationService.editPost(any()))
                .willReturn(new PostServiceResponse());

        //when //then
        mockMvc.perform(
                        post("/boards/1/posts/1/edit")
                                .content(objectMapper.writeValueAsString(request))
                                .session(session)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
    }

}
