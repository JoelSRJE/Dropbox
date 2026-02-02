package project.dropbox.repositories.file;

import org.springframework.data.jpa.repository.JpaRepository;
import project.dropbox.models.file.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
