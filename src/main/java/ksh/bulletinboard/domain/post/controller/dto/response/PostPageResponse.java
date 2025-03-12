package ksh.bulletinboard.domain.post.controller.dto.response;

import ksh.bulletinboard.domain.post.service.dto.response.PostPageServiceResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostPageResponse {

    private int pageNum;
    private int pageSize;
    private int totalPages;
    private boolean hasNext;
    private List<PostPreviewResponse> postResponses;

    public static PostPageResponse from(PostPageServiceResponse response) {
        return new PostPageResponse(response);
    }

    private PostPageResponse(PostPageServiceResponse response) {
        this.pageNum = response.getPageNum();
        this.pageSize = response.getPageSize();
        this.totalPages = response.getTotalPages();
        this.hasNext = response.isHasNext();
        this.postResponses = response.getPostResponses()
                .stream()
                .map(PostPreviewResponse::from)
                .toList();
    }

}
