package ksh.bulletinboard.domain.post.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ksh.bulletinboard.domain.post.service.dto.request.PostEditServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostEditRequest {

    @NotNull
    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public PostEditServiceRequest toServiceRequest() {
        return new PostEditServiceRequest(postId, title, content);
    }

}
