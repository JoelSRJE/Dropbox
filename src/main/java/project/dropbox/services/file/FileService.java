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

    // Dependency för servicen
    private final FileRepository fileRepository;

    /**
     * Hittar ett FileEntity-objekt baserat på fileId och fileOwner.
     *
     * @param fileId - id:et för FileEntity-objektet som ska hittas.
     * @param fileOwner - id:et för ägaren av FileEntity-objektet.
     * @return - ett FileEntity-objekt om det hittas.
     * @throws FileIdIsNullException - OM filens id:et är null.
     * @throws FileOwnerIsNullException - OM ägarens id är null.
     * @throws FileDoesntExistException - OM FileEntity-objektet inte existerar.
     */
    @Override
    public FileEntity findFileByIdAndOwner(UUID fileId, UUID fileOwner) {

        if (fileId == null) {
            throw new FileIdIsNullException();
        }

        if (fileOwner == null) {
            throw new FileOwnerIsNullException();
        }

        return fileRepository.findByFileIdAndFileOwner_UserId(fileId, fileOwner)
                .orElseThrow(() -> new FileDoesntExistException());
    }


    /**
     * Sparar ett FileEntity-objekt i databasen om allt stämmer.
     *
     * @param file - FileEntity-objektet som ska sparas om allt stämmer.
     * @return - Det sparade FileEntity-objektet som sparas.
     * @throws FileNameIsEmptyException - OM fileName är null/blank.
     * @throws FileDataIsNullException - OM data är null.
     * @throws FileOwnerIsNullException - OM fileOwner är null.
     * @throws FileFolderIsNullException - OM fileFolder är null.
     * @throws FileAlreadyExistsException - OM FileEntity-objektet redan existerar i databasen.
     */
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

    /**
     * Raderar ett FileEntity-objekt från databasen.
     *
     * @param fileId - id:et för ett FileEntity-objekt.
     * @param ownerId - id:et för en ägare av ett FileEntity-objekt.
     * @return - det raderade FileEntity-objektet.
     * @throws FileIdIsNullException - OM fileId är null.
     * @throws FileOwnerIdIsNullException - OM ownerId är null.
     * @throws FileDoesntExistException - OM FileEntity-objektet inte existerar i databasen.
     */
    @Override
    public FileEntity deleteFile(UUID fileId, UUID ownerId) {

        if (fileId == null) {
            throw new FileIdIsNullException();
        }

        if (ownerId == null) {
            throw new FileOwnerIdIsNullException();
        }

        FileEntity deletedFile = fileRepository.findByFileIdAndFileOwner_UserId(fileId, ownerId)
                .orElseThrow(() -> new FileDoesntExistException());

        fileRepository.delete(deletedFile);

        return deletedFile;
    }


    /**
     * Hittar alla filer för en folder.
     *
     * @param folderId - id:et för foldern.
     * @param ownerId - id:et för ägaren av foldern.
     * @return - en lista med FileEntity-objekt.
     * @throws FolderIdIsNullException - OM folderId är null.
     * @throws FileOwnerIdIsNullException - OM fileOwner är null.
     */
    @Override
    public List<FileEntity> findFilesByFolder(UUID folderId, UUID ownerId) {

        if (folderId == null) {
            throw new FolderIdIsNullException();
        }

        if (ownerId == null) {
            throw new FileOwnerIdIsNullException();
        }

        return fileRepository.findByFolder_FolderIdAndFileOwner_UserId(folderId, ownerId);
    }


    /**
     * Uppdaterar ett FileEntity-objekt.
     *
     * @param fileId - id:et för ett FileEntity-objekt.
     * @param request - innehåller fileName och ownerId.
     * @return - Det uppdaterade FileEntity-objektet.
     * @throws FileDoesntExistException - OM filen inte existerar i databasen.
     */
    @Override
    public FileEntity updateFileName(UUID fileId, UpdateFileRequest request) {
        FileEntity theFile = fileRepository.findByFileIdAndFileOwner_UserId(fileId, request.ownerId())
                .orElseThrow(() -> new FileDoesntExistException());

        if (request.fileName() != null && !request.fileName().isBlank()) {
            theFile.setFileName(request.fileName());
        }

        fileRepository.save(theFile);

        return theFile;
    }
}
