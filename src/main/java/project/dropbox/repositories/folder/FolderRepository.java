package project.dropbox.repositories.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.dropbox.models.folder.FolderEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<FolderEntity, UUID> {
    List<FolderEntity> findAllByFolderOwner_UserId(UUID ownerId);
    FolderEntity findByFolderIdAndFolderOwner_UserId(UUID folderId, UUID folderOwner);
}
