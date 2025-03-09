package ksh.bulletinboard.domain.reply.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ReplyTest {

    @DisplayName("대댓글의 내용을 수정한다")
    @Test
    void editReply(){
        //given
        Reply reply = Reply.builder()
                .content("수정 전")
                .build();

        //when
        reply.editContent("수정 후");

        //then
        assertThat(reply.getContent()).isEqualTo("수정 후");
     }

}
