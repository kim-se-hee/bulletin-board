package ksh.bulletinboard.domain.post.application;

import ksh.bulletinboard.domain.attachment.service.AttachmentService;
import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.board.service.BoardService;
import ksh.bulletinboard.domain.member.domain.Member;
import ksh.bulletinboard.domain.member.service.MemberService;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.domain.post.service.PostService;
import ksh.bulletinboard.domain.post.service.dto.request.PostEditServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostPageServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.request.PostRegisterServiceRequest;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostRegisterServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostServiceResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Getter
@RequiredArgsConstructor
public class PostApplicationService {

    private final PostService postService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final AttachmentService attachmentService;

    public PostPageServiceResponse getPostsOfBoard(long boardId, PostPageServiceRequest request) {
        Page<Post> page = postService.getPostsOfBoard(boardId, request);
        return PostPageServiceResponse.from(page);
    }

    public PostServiceResponse getSinglePost(long id){
        Post post = postService.getSinglePost(id);
        return PostServiceResponse.from(post);
    }

    public PostRegisterServiceResponse createPost(PostRegisterServiceRequest request) throws IOException {
        Member member = memberService.getById(request.getMemberId());
        Board board = boardService.getById(request.getBoardId());
        Post post = postService.writePost(request, board, member);
        attachmentService.saveAttachments(request.getMultipartFiles(), post);

        return PostRegisterServiceResponse.from(post);
    }

    public PostServiceResponse editPost(PostEditServiceRequest request) {
        Post post = postService.editPost(request);
        return PostServiceResponse.from(post);
    }

    public long increaseView(long postId){
        return postService.increaseView(postId);
    }

}
