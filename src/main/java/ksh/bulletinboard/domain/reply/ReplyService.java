package ksh.bulletinboard.domain.reply;

import ksh.bulletinboard.domain.reply.domain.Reply;
import ksh.bulletinboard.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    List<Reply> getRepliesOfComment(long id){
        return replyRepository.findByCommentId(id);
    }

}
