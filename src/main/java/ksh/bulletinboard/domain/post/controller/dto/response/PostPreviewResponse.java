package ksh.bulletinboard.domain.post.controller.dto.response;

import ksh.bulletinboard.domain.post.service.dto.response.PostPreviewServiceResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostPreviewResponse {

    private long id;
    private String title;
    private long views;
    private LocalDateTime createdAt;

    public static PostPreviewResponse from(PostPreviewServiceResponse response) {
        return new PostPreviewResponse(response);
    }

    private PostPreviewResponse(PostPreviewServiceResponse response) {
        this.id = response.getId();
        this.title = response.getTitle();
        this.views = response.getViews();
        this.createdAt = response.getCreatedAt();
    }

}
