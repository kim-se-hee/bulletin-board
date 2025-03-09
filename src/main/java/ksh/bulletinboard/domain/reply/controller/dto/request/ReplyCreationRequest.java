package ksh.bulletinboard.domain.reply.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyCreationRequest {

    @NotBlank(message = "내용은 필수입니다")
    private String content;

    @NotNull(message = "댓글 id는 필수입니다")
    private Long commentId;

}
