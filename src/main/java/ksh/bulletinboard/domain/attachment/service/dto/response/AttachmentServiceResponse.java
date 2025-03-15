package ksh.bulletinboard.domain.attachment.service.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

@Getter
@NoArgsConstructor
public class AttachmentServiceResponse {

    private String fileName;
    private UrlResource content;

    public AttachmentServiceResponse(String fileName, UrlResource content) {
        this.fileName = fileName;
        this.content = content;
    }

}
