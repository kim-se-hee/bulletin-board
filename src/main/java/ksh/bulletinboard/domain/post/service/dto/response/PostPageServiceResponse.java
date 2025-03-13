package ksh.bulletinboard.domain.post.service.dto.response;

import ksh.bulletinboard.domain.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostPageServiceResponse {

    private int pageNum;
    private int pageSize;
    private int totalPages;
    private boolean hasNext;
    private List<PostPreviewServiceResponse> postResponses  = new ArrayList<>();

    public static PostPageServiceResponse from(Page<Post> page) {
        return new PostPageServiceResponse(page);
    }

    private PostPageServiceResponse(Page<Post> page) {
        this.pageNum = page.getNumber();
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.hasNext = page.hasNext();
        this.postResponses = page.getContent().stream()
                .map(PostPreviewServiceResponse::from)
                .toList();
    }

}
