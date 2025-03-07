package ksh.bulletinboard.domain.post.service;

import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.repository.PostRepository;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostResponse;
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

    public PostPageResponse getPostsOfBoardByTitle(long id, String title, Pageable pageRequest) {
        Page<Post> page = postRepository.findByBoardIdAndTitleContaining(id, title, pageRequest);
        return PostPageResponse.from(page);
    }

    public PostPageResponse getPostsOfBoardByNickname(long id, String nickname, Pageable pageRequest) {
        Page<Post> page = postRepository.findByBoardIdAndMemberNickname(id, nickname, pageRequest);
        return PostPageResponse.from(page);
    }

    @Transactional
    public PostResponse getSinglePost(long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));

        post.increaseViews();
        return PostResponse.from(post);
    }

}
