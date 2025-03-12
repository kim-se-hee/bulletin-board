package ksh.bulletinboard.domain.post.service.dto.response;

import ksh.bulletinboard.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterServiceResponse {

    private long id;
    private String title;

    public static PostRegisterServiceResponse from(Post post) {
        return new PostRegisterServiceResponse(post);
    }

    private PostRegisterServiceResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
    }

}
