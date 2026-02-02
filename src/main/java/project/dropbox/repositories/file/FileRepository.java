package project.dropbox.repositories.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.dropbox.models.file.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
