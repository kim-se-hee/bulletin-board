package ksh.bulletinboard.domain.reply.service;

import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
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

    public List<ReplyServiceResponse> getRepliesOfComment(long id){
        return replyRepository.findByCommentId(id).stream()
                .map(ReplyServiceResponse::from)
                .toList();
    }

}
