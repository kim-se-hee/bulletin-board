package ksh.bulletinboard.domain.attachment.application;

import ksh.bulletinboard.domain.attachment.domain.Attachment;
import ksh.bulletinboard.domain.attachment.service.AttachmentService;
import ksh.bulletinboard.domain.attachment.service.dto.response.AttachmentServiceResponse;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.global.util.FileUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
public class AttachmentApplicationService {

    private final AttachmentService attachmentService;

    public AttachmentServiceResponse getAttachment(long id) throws IOException {
        Attachment attachment = attachmentService.getAttachment(id);
        UrlResource resource = FileUtils.findStoredFileResource(attachment.getStoredFilename());
        String encodedOriginalFileName = FileUtils.encodeFileName(attachment.getOriginalFilename());

        return new AttachmentServiceResponse(encodedOriginalFileName, resource);

    }

    public void saveAttachments(List<MultipartFile> multipartFiles, Post post) throws IOException {
        attachmentService.saveAttachments(multipartFiles, post);
    }

}
