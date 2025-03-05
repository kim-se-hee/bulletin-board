package ksh.bulletinboard.domain.board.domain;

import jakarta.persistence.*;
import ksh.bulletinboard.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Builder
    private Board(String title) {
        this.title = title;
    }

}
