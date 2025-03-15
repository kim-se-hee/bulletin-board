package ksh.bulletinboard.domain.attachment.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ksh.bulletinboard.domain.attachment.service.dto.response.AttachmentServiceResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

@Getter
@NoArgsConstructor
public class AttachmentResponse {

    private String filename;

    private UrlResource content;

    public static AttachmentResponse from(AttachmentServiceResponse serviceResponse) {
        return new AttachmentResponse(serviceResponse);
    }

    private AttachmentResponse(AttachmentServiceResponse serviceResponse) {
        this.filename = serviceResponse.getFileName();
        this.content = serviceResponse.getContent();
    }

}
