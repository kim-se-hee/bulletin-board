package ksh.bulletinboard.domain.post.repository;

import ksh.bulletinboard.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByBoardId(long id, Pageable pageRequest);

    Page<Post> findByBoardIdAndTitleContaining(long id, String title, Pageable pageRequest);

}
