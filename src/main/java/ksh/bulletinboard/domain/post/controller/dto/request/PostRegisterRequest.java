package ksh.bulletinboard.domain.post.controller.dto.request;

import ksh.bulletinboard.domain.post.service.dto.request.PostRegisterServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRegisterRequest {

    String title;
    String content;
    long memberId;
    long boardId;

    public PostRegisterServiceRequest toServiceRequest() {
        return PostRegisterServiceRequest.of(title, content, memberId, boardId);
    }

}
