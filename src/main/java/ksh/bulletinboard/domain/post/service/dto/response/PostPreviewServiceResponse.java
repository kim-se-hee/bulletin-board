package ksh.bulletinboard.domain.post.service.dto.response;

import ksh.bulletinboard.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostPreviewServiceResponse {

    private long id;
    private String title;
    private long views;
    private LocalDateTime createdAt;

    public static PostPreviewServiceResponse from(Post post) {
        return new PostPreviewServiceResponse(post);
    }

    private PostPreviewServiceResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.views = post.getViews();
        this.createdAt = post.getCreatedAt();
    }

}
