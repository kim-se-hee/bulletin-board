package ksh.bulletinboard.domain.post.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import ksh.bulletinboard.domain.post.service.dto.request.PostRegisterServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRegisterRequest {

    @NotBlank(message = "제목은 필수입니다")
    String title;

    @NotBlank(message = "내용은 필수입니다")
    String content;

    List<MultipartFile> files = new ArrayList<>();

    public PostRegisterServiceRequest toServiceRequest(long boardId, long memberId) {
        return new PostRegisterServiceRequest(title, content, files, boardId, memberId);
    }

    public PostRegisterRequest(String title, String content, List<MultipartFile> files) {
        this.title = title;
        this.content = content;
        this.files = files;
    }

}
