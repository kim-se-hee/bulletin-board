package ksh.bulletinboard.domain.post.repository;

import ksh.bulletinboard.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    Page<Post> findPostsOfBoard(long boardId, String title, String nickname, Pageable pageable);

}
