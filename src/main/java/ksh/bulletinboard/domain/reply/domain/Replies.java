package ksh.bulletinboard.domain.reply.domain;

import ksh.bulletinboard.domain.reply.service.dto.ReplyServiceResponse;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Replies {

    private List<Reply> replies;

    public static Replies of(List<Reply> replies){
        return new Replies(replies);
    }

    public Map<Long, List<Reply>> groupingByCommentId(){
        return replies.stream()
                .collect(Collectors.groupingBy(reply -> reply.getComment().getId()));
    }

    public List<ReplyServiceResponse> toServiceResponse() {
        return replies.stream()
                .map(ReplyServiceResponse::from)
                .toList();
    }

    private Replies(List<Reply> replies){
        this.replies = replies;
    }

}
