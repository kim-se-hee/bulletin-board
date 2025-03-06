package ksh.bulletinboard.domain.post.service.dto.response;

import ksh.bulletinboard.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse {

    private long id;
    private String title;
    private String content;
    private long views;

    public static PostResponse from(Post post) {
        return new PostResponse(post);
    }

    private PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.views = post.getViews();
    }

}
