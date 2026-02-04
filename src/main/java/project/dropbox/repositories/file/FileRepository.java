package project.dropbox.repositories.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.dropbox.models.file.FileEntity;
import project.dropbox.models.folder.FolderEntity;
import project.dropbox.models.user.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    Optional<FileEntity> findByFileIdAndFileOwner_Id(UUID fileId, UUID ownerId);
    boolean existsByFileNameAndFolderAndFileOwner(String fileName, FolderEntity folder, User fileOwner);
    List<FileEntity> findByFolder_FolderIdAndFileOwner_Id(UUID folderId, UUID ownerId);
}