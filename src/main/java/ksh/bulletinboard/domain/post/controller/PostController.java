package ksh.bulletinboard.domain.post.controller;

import jakarta.validation.Valid;
import ksh.bulletinboard.domain.post.controller.dto.request.PostEditRequest;
import ksh.bulletinboard.domain.post.controller.dto.request.PostPageRequest;
import ksh.bulletinboard.domain.post.controller.dto.request.PostRegisterRequest;
import ksh.bulletinboard.domain.post.controller.dto.response.PostPageResponse;
import ksh.bulletinboard.domain.post.controller.dto.response.PostResponse;
import ksh.bulletinboard.domain.post.application.PostApplicationService;
import ksh.bulletinboard.domain.post.service.dto.response.PostRegisterServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostApplicationService postFacade;

    @GetMapping("/boards/{boardId}/posts/{postId}")
    public ResponseEntity<PostResponse> post(@PathVariable("postId") long postId) {
        PostResponse response = PostResponse.from(postFacade.getSinglePost(postId));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/boards/{boardId}/posts")
    public ResponseEntity<PostPageResponse> posts(@PathVariable("boardId") long boardId, @Valid @ModelAttribute PostPageRequest request) {
        PostPageResponse response = PostPageResponse.from(postFacade.getPostsOfBoard(boardId, request.toServiceRequest()));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping(value =
            "/boards/{boardId}/posts/new",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PostRegisterServiceResponse> write(
            @PathVariable("boardId") Long boardId,
            @Valid @ModelAttribute PostRegisterRequest request,
            @SessionAttribute("memberId")  long memberId
    ) throws IOException {
        PostRegisterServiceResponse response = postFacade.createPost(request.toServiceRequest(boardId, memberId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/boards/{boardId}/posts/{postId}/edit")
    public ResponseEntity<PostResponse> edit(@Valid @RequestBody PostEditRequest request) {
        PostResponse response = PostResponse.from(postFacade.editPost(request.toServiceRequest()));
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping("/boards/posts/{postId}/views")
    public ResponseEntity<Long> increaseViews(@PathVariable("postId") long postId) {
        long updatedViews = postFacade.increaseView(postId);

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedViews);
    }



}
