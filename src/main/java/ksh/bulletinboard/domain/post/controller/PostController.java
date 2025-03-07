package ksh.bulletinboard.domain.post.controller;

import ksh.bulletinboard.domain.post.service.PostService;
import ksh.bulletinboard.domain.post.service.dto.response.PostPageServiceResponse;
import ksh.bulletinboard.domain.post.service.dto.response.PostServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/board/{boardId}/post/{postId}")
    public ResponseEntity<PostServiceResponse> post(@PathVariable("postId") long postId) {
        PostServiceResponse response = postService.getSinglePost(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/board/{boardId}/posts")
    public ResponseEntity<PostPageServiceResponse> posts(
            @PathVariable("boardId") long boardId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "nickname", required = false) String nickname,
            Pageable pageRequest
    ) {
        PostPageServiceResponse response;
        if(title != null){
            response = postService.getPostsOfBoardByTitle(boardId, title, pageRequest);
        }else if(nickname != null){
            response = postService.getPostsOfBoardByNickname(boardId, nickname, pageRequest);
        }else{
            response = postService.getPostsOfBoard(boardId, pageRequest);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
