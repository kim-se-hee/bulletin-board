package ksh.bulletinboard.domain.reply.domain;

import jakarta.persistence.*;
import ksh.bulletinboard.domain.BaseEntity;
import ksh.bulletinboard.domain.comment.domain.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Builder
    private Reply(String content, Comment comment) {
        this.content = content;
        this.comment = comment;
    }

}
