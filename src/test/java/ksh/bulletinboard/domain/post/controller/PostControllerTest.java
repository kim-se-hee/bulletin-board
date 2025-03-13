package ksh.bulletinboard.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ksh.bulletinboard.domain.post.controller.dto.request.PostRegisterRequest;
import ksh.bulletinboard.domain.post.service.PostService;
import ksh.bulletinboard.domain.post.service.dto.request.PostEditServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostPageServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostRegisterServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostServiceResponse;
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
import static org.mockito.BDDMockito.*;
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
        //given
        PostServiceResponse serviceResponse = new PostServiceResponse();
        given(postService.getSinglePost(anyLong()))
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
        given(postService.getPostsOfBoard(anyLong(), any(PostPageServiceRequest.class)))
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
        given(postService.getPostsOfBoard(anyLong(), any(PostPageServiceRequest.class)))
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
        given(postService.getPostsOfBoard(anyLong(), any(PostPageServiceRequest.class)))
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
        PostRegisterRequest request = new PostRegisterRequest("제목", "내용", 1L);

        given(postService.writePost(any()))
                .willReturn(new PostRegisterServiceResponse());

        //when //then
        mockMvc.perform(
                        post("/boards/1/posts/new")
                                .content(objectMapper.writeValueAsString(request))
                                .session(session)
                                .contentType(MediaType.APPLICATION_JSON)
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

        given(postService.editPost(any()))
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
