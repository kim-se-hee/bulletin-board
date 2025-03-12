package ksh.bulletinboard.domain.post.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostPageServiceRequest {

    String title;

    String nickname;

    int pageNum;

    int pageSize;

    public PostPageServiceRequest(String title, String nickname, int pageNum, int pageSize) {
        this.title = title;
        this.nickname = nickname;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}
