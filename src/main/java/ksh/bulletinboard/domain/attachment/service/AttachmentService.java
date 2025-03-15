package ksh.bulletinboard.domain.attachment.service;

import ksh.bulletinboard.domain.attachment.domain.Attachment;
import ksh.bulletinboard.domain.attachment.repository.AttachmentRepository;
import ksh.bulletinboard.domain.attachment.service.dto.response.AttachmentServiceResponse;
import ksh.bulletinboard.global.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceResponse getAttachment(long id) throws MalformedURLException {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파일입니다"));

        String fullPath = FileUtils.createFullPath(attachment.getStoredFilename());
        UrlResource resource = new UrlResource("file:" + fullPath);

        String encodedOriginalFileName = UriUtils.encode(attachment.getOriginalFilename(), StandardCharsets.UTF_8);

        return new AttachmentServiceResponse(encodedOriginalFileName, resource);

    }


}
