package ksh.bulletinboard.domain.post.controller.dto.response;

import ksh.bulletinboard.domain.post.service.dto.response.PostRegisterServiceResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterResponse {

    private long id;
    private String title;

    public static PostRegisterResponse from(PostRegisterServiceResponse response) {
        return new PostRegisterResponse(response);
    }

    private PostRegisterResponse(PostRegisterServiceResponse response) {
        this.id = response.getId();
        this.title = response.getTitle();
    }

}
