package ksh.bulletinboard.domain.post.controller;

import jakarta.validation.Valid;
import ksh.bulletinboard.domain.post.controller.dto.request.PostEditRequest;
import ksh.bulletinboard.domain.post.controller.dto.request.PostRegisterRequest;
import ksh.bulletinboard.domain.post.controller.dto.response.PostPageResponse;
import ksh.bulletinboard.domain.post.controller.dto.response.PostRegisterResponse;
import ksh.bulletinboard.domain.post.controller.dto.response.PostResponse;
import ksh.bulletinboard.domain.post.service.PostService;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostRegisterServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/boards/{boardId}/posts/{postId}")
    public ResponseEntity<PostResponse> post(@PathVariable("postId") long postId) {
        PostResponse response = PostResponse.from(postService.getSinglePost(postId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/boards/{boardId}/posts")
    public ResponseEntity<PostPageResponse> posts(
            @PathVariable("boardId") long boardId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "nickname", required = false) String nickname,
            Pageable pageRequest
    ) {
        PostPageServiceResponse page;
        if(title != null){
            page = postService.getPostsOfBoardByTitle(boardId, title, pageRequest);
        }else if(nickname != null){
            page = postService.getPostsOfBoardByNickname(boardId, nickname, pageRequest);
        }else{
            page = postService.getPostsOfBoard(boardId, pageRequest);
        }

        PostPageResponse response = PostPageResponse.from(page);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/boards/{boardId}/posts")
    public ResponseEntity<PostRegisterResponse> write(
            @Valid @RequestBody PostRegisterRequest request,
            @SessionAttribute("memberId") long memberId

    ) {
        PostRegisterResponse response = PostRegisterResponse.from(postService.writePost(request.toServiceRequest(memberId)));
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/boards/{boardId}/posts/{postId}")
    public ResponseEntity<PostResponse> edit(@Valid @RequestBody PostEditRequest request) {
        PostResponse response = PostResponse.from(postService.editPost(request.toServiceRequest()));
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/boards/posts/{postId}/views")
    public ResponseEntity<Long> increaseViews(@PathVariable("postId") long postId) {
        long updatedViews = postService.increaseView(postId);

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedViews);
    }

}
