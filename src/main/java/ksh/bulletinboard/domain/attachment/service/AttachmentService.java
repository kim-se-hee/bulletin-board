package ksh.bulletinboard.domain.attachment.service;

import ksh.bulletinboard.domain.attachment.domain.Attachment;
import ksh.bulletinboard.domain.attachment.repository.AttachmentRepository;
import ksh.bulletinboard.domain.post.domain.Post;
import ksh.bulletinboard.global.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public Attachment getAttachment(long id) throws MalformedURLException {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파일입니다"));
    }

    //TODO:글 등록 시 첨부파일 명이 보여야 하므로 생성한 첨부파일 리스트 반환하기
    //TODO:forEach, stream을 통한 리팩토링
    public void saveAttachments(List<MultipartFile> multipartFiles, Post post) throws IOException {
        for (MultipartFile multipartFile : multipartFiles) {
            String storedName = FileUtils.saveFile(multipartFile);

            Attachment attachment = Attachment.builder()
                    .originalFilename(multipartFile.getOriginalFilename())
                    .storedFilename(storedName)
                    .post(post)
                    .build();
            attachmentRepository.save(attachment);
        }
    }

}
