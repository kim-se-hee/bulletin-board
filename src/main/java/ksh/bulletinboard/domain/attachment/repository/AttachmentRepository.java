package ksh.bulletinboard.domain.attachment.repository;

import ksh.bulletinboard.domain.attachment.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
