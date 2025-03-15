package ksh.bulletinboard.domain.attachment.domain;

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
public class Attachment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attachment_id")
    private Long id;

    private String originalFilename;

    private String storedFilename;

    private int size;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Builder
    private Attachment(String originalFilename, String storedFilename, int size, Post post) {
        this.originalFilename = originalFilename;
        this.storedFilename = storedFilename;
        this.size = size;
        this.post = post;
    }

}
