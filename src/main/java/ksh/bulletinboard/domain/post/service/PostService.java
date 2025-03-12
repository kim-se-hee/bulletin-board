package ksh.bulletinboard.domain.post.service;

import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.board.repository.BoardRepository;
import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.member.repository.MemberRepository;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.repository.PostRepository;
import ksh.bulletinboard.domain.post.service.dto.request.PostEditServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostRegisterServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostRegisterServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostServiceResponse;
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
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public PostPageServiceResponse getPostsOfBoard(long id, Pageable pageRequest) {
        Page<Post> page = postRepository.findByBoardId(id, pageRequest);
        return PostPageServiceResponse.from(page);
    }

    public PostPageServiceResponse getPostsOfBoardByTitle(long id, String title, Pageable pageRequest) {
        Page<Post> page = postRepository.findByBoardIdAndTitleContaining(id, title, pageRequest);
        return PostPageServiceResponse.from(page);
    }

    public PostPageServiceResponse getPostsOfBoardByNickname(long id, String nickname, Pageable pageRequest) {
        Page<Post> page = postRepository.findByBoardIdAndMemberNickname(id, nickname, pageRequest);
        return PostPageServiceResponse.from(page);
    }

    public PostServiceResponse getSinglePost(long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));

        return PostServiceResponse.from(post);
    }

    @Transactional
    public PostRegisterServiceResponse writePost(PostRegisterServiceRequest request) {

        Member writer = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다"));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .views(1L)
                .member(writer)
                .board(board)
                .build();
        postRepository.save(post);

        return PostRegisterServiceResponse.from(post);
    }

    @Transactional
    public PostServiceResponse editPost(PostEditServiceRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));

        post.editTitleAndContent(request.getTitle(), request.getContent());

        return PostServiceResponse.from(post);
    }

    /*
     * AOP 학습 후 재시도 로직 추가
     */
    @Transactional
    public long increaseView(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));

        post.increaseViews();
        return post.getViews();
    }

}
