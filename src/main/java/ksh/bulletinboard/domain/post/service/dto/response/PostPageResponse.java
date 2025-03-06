package ksh.bulletinboard.domain.post.service.dto.response;

import ksh.bulletinboard.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostPageResponse {

    private int pageNum;
    private int pageSize;
    private int totalPages;
    private boolean hasNext;
    private List<PostResponse> postResponses;

    public static PostPageResponse from(Page<Post> page) {
        return new PostPageResponse(page);
    }

    private  PostPageResponse(Page<Post> page) {
        this.pageNum = page.getNumber();
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.hasNext = page.hasNext();
        this.postResponses = page.getContent().stream()
                .map(PostResponse::from)
                .toList();
    }

}
