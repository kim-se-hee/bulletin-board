package ksh.bulletinboard.domain.attachment.controller;

import ksh.bulletinboard.domain.attachment.controller.dto.AttachmentResponse;
import ksh.bulletinboard.domain.attachment.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping(
            value = "/boards/posts/{postId}/attachments/{attachmentId}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<Resource> attachment(@PathVariable("attachmentId") long id) throws MalformedURLException {
        AttachmentResponse response = AttachmentResponse.from(attachmentService.getAttachment(id));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Content-Disposition", "attachment; filename=\"" + response.getFilename() + "\"")
                .body(response.getContent());
    }

}
