package ksh.bulletinboard.domain.post.domain;

import jakarta.persistence.*;
import ksh.bulletinboard.domain.BaseEntity;
import ksh.bulletinboard.domain.board.domain.Board;
import ksh.bulletinboard.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private Long views;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    private Post(String title, String content, Long views, Board board, Member member) {
        this.title = title;
        this.content = content;
        this.views = views;
        this.board = board;
        this.member = member;
    }

    public void increaseViews() {
        this.views++;
    }

    public void editTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
