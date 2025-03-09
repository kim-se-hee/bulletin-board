package ksh.bulletinboard.domain.post.service.dto.response;

import ksh.bulletinboard.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterResponse {

    private long id;
    private String title;

    public static PostRegisterResponse from(Post post) {
        return new PostRegisterResponse(post);
    }

    private PostRegisterResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
    }

}
