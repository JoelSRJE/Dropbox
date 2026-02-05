package project.dropbox.services.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.dropbox.exceptions.folder.FolderDoesntExistException;
import project.dropbox.exceptions.folder.FolderNameIsEmptyException;
import project.dropbox.exceptions.folder.FolderOwnerIsEmptyException;
import project.dropbox.exceptions.folder.FolderOwnerIsntSameException;
import project.dropbox.exceptions.user.UserDoesntExistsException;
import project.dropbox.models.folder.FolderEntity;
import project.dropbox.models.user.User;
import project.dropbox.repositories.folder.FolderRepository;
import project.dropbox.repositories.user.UserRepository;
import project.dropbox.requests.folder.CreateFolderRequest;
import project.dropbox.requests.folder.UpdateFolderRequest;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FolderService implements IFolderService {

    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    @Override
    public FolderEntity findFolderById(UUID folderId, UUID folderOwner) {
        FolderEntity foundFolder = folderRepository.findByFolderIdAndFolderOwner_UserId(folderId, folderOwner);

        if (foundFolder == null) {
            throw new FolderDoesntExistException();
        }

        return foundFolder;
    }

    @Override
    public FolderEntity createFolder(CreateFolderRequest request) {

        if (request.folderName() == null || request.folderName().isBlank()) {
            throw new FolderNameIsEmptyException();
        }

        if (request.ownerId() == null) {
            throw new FolderOwnerIsEmptyException();
        }

        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new FolderOwnerIsEmptyException());

        FolderEntity parent = null;

        if (request.parentFolder() != null) {
            parent = folderRepository.findById(request.parentFolder())
                    .orElseThrow(() -> new FolderDoesntExistException());
        }

        FolderEntity newFolder = new FolderEntity(
                request.folderName(),
                owner,
                parent
        );

        return folderRepository.save(newFolder);
    }

    @Override
    public FolderEntity deleteFolder(UUID folderId, UUID folderOwner) {
        FolderEntity deleteFolder = findFolderById(folderId, folderOwner);

        if (!deleteFolder.getFolderOwner().getUserId().equals(folderOwner)) {
            throw new FolderOwnerIsntSameException();
        }

        folderRepository.delete(deleteFolder);

        return deleteFolder;
    }

    @Override
    public List<FolderEntity> getAllFoldersByUser(UUID ownerId) {
        return folderRepository.findAllByFolderOwner_UserId(ownerId);
    }

    @Override
    public FolderEntity updateFolderName(UUID folderId, UpdateFolderRequest request) {
        FolderEntity updateFolder = folderRepository.findById(folderId)
                .orElseThrow(() -> new FolderDoesntExistException());

        if (request.folderName() != null && !request.folderName().isBlank()) {
            updateFolder.setFolderName(request.folderName());
        }

        folderRepository.save(updateFolder);

        return updateFolder;
    }
}
