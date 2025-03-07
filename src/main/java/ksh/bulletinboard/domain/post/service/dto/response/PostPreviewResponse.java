package ksh.bulletinboard.domain.post.service.dto.response;

import ksh.bulletinboard.domain.post.domain.Post;
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

    public static PostPreviewResponse from(Post post) {
        return new PostPreviewResponse(post);
    }

    private PostPreviewResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.views = post.getViews();
        this.createdAt = post.getCreatedAt();
    }

}
