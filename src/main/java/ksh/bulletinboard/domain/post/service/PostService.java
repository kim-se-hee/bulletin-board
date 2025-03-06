package ksh.bulletinboard.domain.post.service;

import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.repository.PostRepository;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostPageResponse getPostsOfBoard(long id, Pageable pageRequest) {
        Page<Post> page = postRepository.findByBoardId(id, pageRequest);
        return PostPageResponse.from(page);
    }

}
