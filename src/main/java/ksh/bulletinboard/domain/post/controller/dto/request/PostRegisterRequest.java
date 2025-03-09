package ksh.bulletinboard.domain.post.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ksh.bulletinboard.domain.post.service.dto.request.PostRegisterServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterRequest {

    @NotBlank
    String title;

    @NotBlank
    String content;

    @NotNull
    Long memberId;

    @NotNull
    Long boardId;

    public PostRegisterServiceRequest toServiceRequest() {
        return PostRegisterServiceRequest.of(title, content, memberId, boardId);
    }

    public PostRegisterRequest(String title, String content, Long memberId, Long boardId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.boardId = boardId;
    }

}
