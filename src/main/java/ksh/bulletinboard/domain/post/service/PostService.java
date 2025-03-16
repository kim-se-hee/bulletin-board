package ksh.bulletinboard.domain.post.service;

import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.repository.PostRepository;
import ksh.bulletinboard.domain.post.service.dto.request.PostEditServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostPageServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostRegisterServiceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getPostsOfBoard(long boardId, PostPageServiceRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNum(), request.getPageSize());
        return postRepository.findPostsOfBoard(boardId, request.getTitle(), request.getNickname(), pageRequest);
    }

    public Post getSinglePost(long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));
    }

    @Transactional
    public Post writePost(PostRegisterServiceRequest request, Board board, Member member) throws IOException {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .views(1L)
                .member(member)
                .board(board)
                .build();
        return postRepository.save(post);
    }

    @Transactional
    public Post editPost(PostEditServiceRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다"));

        post.editTitleAndContent(request.getTitle(), request.getContent());
        return post;
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
