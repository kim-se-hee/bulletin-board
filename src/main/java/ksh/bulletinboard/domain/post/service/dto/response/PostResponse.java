package ksh.bulletinboard.domain.post.service.dto.response;

import ksh.bulletinboard.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponse {

    private long id;
    private String title;
    private long views;
    private String content;
    private LocalDateTime createdAt;

    public static PostResponse from(Post post) {
        return new PostResponse(post);
    }

    private PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.views = post.getViews();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

}
