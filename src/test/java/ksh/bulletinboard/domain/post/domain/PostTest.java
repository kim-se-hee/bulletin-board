package ksh.bulletinboard.domain.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @DisplayName("게시글을 조회하면 조회수가 증가한다")
    @Test
    void increaseViews() {
        //given
        Post post = Post.builder()
                .views(1L)
                .build();

        //when
        post.increaseViews();

        //then
        assertThat(post.getViews()).isEqualTo(2L);
     }

    @DisplayName("제목과 글 내용을 수정한다")
    @Test
    void editTitleAndContent() {
        //given
        Post post = Post.builder()
                .title("수정 전 제목")
                .content("수정 전 내용")
                .build();

        //when
        post.editTitleAndContent("수정 후 제목", "수정 후 내용");

        //then
        assertThat(post)
                .extracting("title", "content")
                .containsExactly("수정 후 제목", "수정 후 내용");
    }

}
