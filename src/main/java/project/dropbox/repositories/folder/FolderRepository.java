package project.dropbox.repositories.folder;

import org.springframework.data.jpa.repository.JpaRepository;
import project.dropbox.models.folder.FolderEntity;

import java.util.UUID;

public interface FolderRepository extends JpaRepository<FolderEntity, UUID> {
}
