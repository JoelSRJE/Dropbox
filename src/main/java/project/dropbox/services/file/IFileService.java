package project.dropbox.services.file;

import project.dropbox.models.file.FileEntity;
import project.dropbox.requests.file.CreateFileRequest;
import project.dropbox.requests.file.UpdateFileRequest;

import java.io.File;
import java.util.List;
import java.util.UUID;

// Kräver Authorization
public interface IFileService {
    // Hittar filen baserat på dess ID och ägarens id.
    List<FileEntity> findFilesByOwner(UUID ownerId);

    // Skapar ett nytt FileEntity objekt som sedan sparas i databasen.
    FileEntity createFile(CreateFileRequest request, UUID userId);

    // Raderar en fil baserat på dess id samt ägarens id.
    FileEntity deleteFile(UUID fileId, UUID ownerId);

    // Hittar filer från en specifik folder.
    List<FileEntity> findFilesByFolder(UUID folderId, UUID ownerId);

    // Hittar filen utifrån dess id.
    FileEntity getFileByIdAndUser(UUID fileId, UUID userId);

    // Låter användaren uppdatera en fils namn, och endast namnet.
    FileEntity updateFileName(UUID fileId, UpdateFileRequest request, UUID userId);
}
