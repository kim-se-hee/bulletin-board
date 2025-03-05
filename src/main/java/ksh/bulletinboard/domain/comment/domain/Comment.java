package ksh.bulletinboard.domain.comment.domain;

import jakarta.persistence.*;
import ksh.bulletinboard.domain.BaseEntity;
import ksh.bulletinboard.domain.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @Builder
    private Comment(String content, Post post, Comment parent) {
        this.content = content;
        this.post = post;
        this.parent = parent;
    }

}
