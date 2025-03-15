package ksh.bulletinboard.domain.post.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostRegisterServiceRequest {

    String title;
    String content;
    List<MultipartFile> multipartFiles;
    long boardId;
    long memberId;

    public PostRegisterServiceRequest(String title, String content, List<MultipartFile> multipartFiles, long boardId, long memberId) {
        this.title = title;
        this.content = content;
        this.multipartFiles = multipartFiles;
        this.boardId = boardId;
        this.memberId = memberId;
    }

}
