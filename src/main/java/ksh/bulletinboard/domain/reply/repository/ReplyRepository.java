package ksh.bulletinboard.domain.reply.repository;

import ksh.bulletinboard.domain.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    List<Reply> findByCommentId(long id);

}
