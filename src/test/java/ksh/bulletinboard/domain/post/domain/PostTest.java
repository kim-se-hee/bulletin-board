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

}
