package ksh.bulletinboard.domain.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ksh.bulletinboard.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static ksh.bulletinboard.domain.post.domain.QPost.post;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Post> findPostsOfBoard(long boardId, String title, String nickname, Pageable pageRequest) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(post.board.id.eq(boardId));

        if (title != null) {
            builder.and(post.title.eq(title));
        }

        if (nickname != null) {
            builder.and(post.member.nickname.eq(nickname));
        }

        List<Post> posts = queryFactory
                .select(post)
                .from(post)
                .where(builder)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(posts, pageRequest, total == null ? 0 : total);
    }

}
