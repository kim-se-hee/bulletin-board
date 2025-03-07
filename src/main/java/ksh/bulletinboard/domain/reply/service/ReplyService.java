package ksh.bulletinboard.domain.reply.service;

import ksh.bulletinboard.domain.reply.dto.ReplyResponse;
import ksh.bulletinboard.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;

    List<ReplyResponse> getRepliesOfComment(long id){
        return replyRepository.findByCommentId(id).stream()
                .map(ReplyResponse::from)
                .toList();
    }

}
