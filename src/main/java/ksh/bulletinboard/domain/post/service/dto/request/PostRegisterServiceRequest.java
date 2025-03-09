package ksh.bulletinboard.domain.post.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterServiceRequest {

    String title;
    String content;
    long memberId;
    long boardId;

    public static PostRegisterServiceRequest of(String title, String content, long memberId, long boardId) {
        return new PostRegisterServiceRequest(title, content, memberId, boardId);
    }

    private PostRegisterServiceRequest(String title, String content, long memberId, long boardId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.boardId = boardId;
    }
}
