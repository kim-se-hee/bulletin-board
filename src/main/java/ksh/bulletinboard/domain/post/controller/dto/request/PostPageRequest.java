package ksh.bulletinboard.domain.post.controller.dto.request;

import ksh.bulletinboard.domain.post.service.dto.request.PostPageServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostPageRequest {

    String title;

    String nickname;

    int pageNum;

    int pageSize;

    public PostPageServiceRequest toServiceRequest() {
        return new PostPageServiceRequest(title, nickname, pageNum, pageSize);
    }

}
