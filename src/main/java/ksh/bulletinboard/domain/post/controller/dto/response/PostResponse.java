package ksh.bulletinboard.domain.post.controller.dto.response;

import ksh.bulletinboard.domain.post.service.dto.response.PostServiceResponse;
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
    private String author;
    private LocalDateTime createdAt;

    public static PostResponse from(PostServiceResponse response) {
        return new PostResponse(response);
    }

    private PostResponse(PostServiceResponse response) {
        this.id = response.getId();
        this.title = response.getTitle();
        this.views = response.getViews();
        this.content = response.getContent();
        this.author = response.getAuthor();
        this.createdAt = response.getCreatedAt();
    }

}
