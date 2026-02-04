package project.dropbox.services.file;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.dropbox.exceptions.file.*;
import project.dropbox.exceptions.folder.FolderIdIsNullException;
import project.dropbox.models.file.FileEntity;
import project.dropbox.repositories.file.FileRepository;
import project.dropbox.requests.file.UpdateFileRequest;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService implements IFileService {

    private final FileRepository fileRepository;

    @Override
    public FileEntity findFileByIdAndOwner(UUID fileId, UUID fileOwner) {

        if (fileId == null) {
            throw new FileIdIsNullException();
        }

        if (fileOwner == null) {
            throw new FileOwnerIsNullException();
        }

        return fileRepository.findByFileIdAndFileOwner_Id(fileId, fileOwner)
                .orElseThrow(() -> new FileDoesntExistException());
    }

    @Override
    public FileEntity createFile(FileEntity file) {

        if (file.getFileName() == null || file.getFileName().isBlank()) {
            throw new FileNameIsEmptyException();
        }

        if (file.getData() == null) {
            throw new FileDataIsNullException();
        }

        if (file.getFileOwner() == null) {
            throw new FileOwnerIsNullException();
        }

        if (file.getFolder() == null) {
            throw new FileFolderIsNullException();
        }

        boolean exists = fileRepository.existsByFileNameAndFolderAndFileOwner(
                file.getFileName(), file.getFolder(), file.getFileOwner()
        );

        if (exists) {
            throw new FileAlreadyExistsException();
        }

        return fileRepository.save(file);
    }

    /*
    1. Delete funktionen tar emot fileId och ownerId som parametrar
    och verifieras via simpla null checks.
    2. Verifierar att filen existerar och därefter raderar den från databasen.
    3. Skickar därefter tillbaka filen som raderades
     */
    @Override
    public FileEntity deleteFile(UUID fileId, UUID ownerId) {

        if (fileId == null) {
            throw new FileIdIsNullException();
        }

        if (ownerId == null) {
            throw new FileOwnerIdIsNullException();
        }

        FileEntity deletedFile = fileRepository.findByFileIdAndFileOwner_Id(fileId, ownerId)
                .orElseThrow(() -> new FileDoesntExistException());

        fileRepository.delete(deletedFile);

        return deletedFile;
    }

    // Skickar tillbaka alla filer för en folder i en List.
    @Override
    public List<FileEntity> findFilesByFolder(UUID folderId, UUID ownerId) {

        if (folderId == null) {
            throw new FolderIdIsNullException();
        }

        if (ownerId == null) {
            throw new FileOwnerIdIsNullException();
        }

        return fileRepository.findByFolder_FolderIdAndFileOwner_Id(folderId, ownerId);
    }


    /*
    Uppdaterar en fil utifrån fileId, request.ownerId samt request.fileName.

    findByFileIdAndFileOwner_Id hittar själva filen

    inuti if-statement hanterar vi uppdateringen med setter från dess Modell och sedan sparas alltihop.
    */
    @Override
    public FileEntity updateFileName(UUID fileId, UpdateFileRequest request) {
        FileEntity theFile = fileRepository.findByFileIdAndFileOwner_Id(fileId, request.ownerId())
                .orElseThrow(() -> new FileDoesntExistException());

        if (request.fileName() != null && !request.fileName().isBlank()) {
            theFile.setFileName(request.fileName());
        }

        fileRepository.save(theFile);

        return theFile;
    }
}
