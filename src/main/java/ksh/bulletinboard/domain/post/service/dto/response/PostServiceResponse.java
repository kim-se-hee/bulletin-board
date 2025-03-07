package ksh.bulletinboard.domain.post.service.dto.response;

import ksh.bulletinboard.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostServiceResponse {

    private long id;
    private String title;
    private long views;
    private String content;
    private LocalDateTime createdAt;

    public static PostServiceResponse from(Post post) {
        return new PostServiceResponse(post);
    }

    private PostServiceResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.views = post.getViews();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
    }

}
