package ksh.bulletinboard.domain.reply.service;

import ksh.bulletinboard.domain.reply.service.dto.ReplyResponse;
import ksh.bulletinboard.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;

    public List<ReplyResponse> getRepliesOfComment(long id){
        return replyRepository.findByCommentId(id).stream()
                .map(ReplyResponse::from)
                .toList();
    }

}
