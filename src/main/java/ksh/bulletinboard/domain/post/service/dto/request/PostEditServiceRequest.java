package ksh.bulletinboard.domain.post.service.dto.request;

import lombok.Getter;

@Getter
public class PostEditServiceRequest {

    private Long postId;
    private String title;
    private String content;

    public PostEditServiceRequest(Long postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

}
