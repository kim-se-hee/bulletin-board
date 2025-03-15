package ksh.bulletinboard.domain.post.service;

import ksh.bulletinboard.domain.attachment.domain.Attachment;
import ksh.bulletinboard.domain.attachment.repository.AttachmentRepository;
import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.board.repository.BoardRepository;
import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.member.repository.MemberRepository;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.repository.PostRepository;
import ksh.bulletinboard.domain.post.service.dto.request.PostEditServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostPageServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostRegisterServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostRegisterServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostServiceResponse;
import ksh.bulletinboard.global.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final AttachmentRepository attachmentRepository;

    public PostPageServiceResponse getPostsOfBoard(long boardId, PostPageServiceRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPageNum(), request.getPageSize());
        Page<Post> page = postRepository.findPostsOfBoard(boardId, request.getTitle(), request.getNickname(), pageRequest);
        return PostPageServiceResponse.from(page);
    }

    public PostServiceResponse getSinglePost(long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));

        return PostServiceResponse.from(post);
    }

    @Transactional
    public PostRegisterServiceResponse writePost(PostRegisterServiceRequest request) throws IOException {

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

        List<MultipartFile> multipartFiles = request.getMultipartFiles();

        for (MultipartFile multipartFile : multipartFiles) {
            String originalName = multipartFile.getOriginalFilename();
            String storedName = FileUtils.createStoredFileName(originalName);
            String fullPath = FileUtils.createFullPath(storedName);
            multipartFile.transferTo(new File(fullPath));

            Attachment attachment = Attachment.builder()
                    .originalFilename(multipartFile.getOriginalFilename())
                    .storedFilename(storedName)
                    .post(post)
                    .build();
            attachmentRepository.save(attachment);
        }

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
