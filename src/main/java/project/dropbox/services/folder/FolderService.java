package project.dropbox.services.folder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.dropbox.exceptions.folder.FolderDoesntExistException;
import project.dropbox.exceptions.folder.FolderNameIsEmptyException;
import project.dropbox.exceptions.folder.FolderOwnerIsEmptyException;
import project.dropbox.exceptions.folder.FolderOwnerIsntSameException;
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

    // Dependencies för servicen
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    /**
     * Hittar en folder baserat på dess id och verifierar att den tillhör användaren.
     *
     * @param folderId - id:et för folder.
     * @param folderOwner - användar id för ägaren.
     * @return - ett FolderEntity objekt.
     * @throws FolderDoesntExistException - OM objektet inte existerar eller tillhör användaren.
     */
    @Override
    public FolderEntity findFolderById(UUID folderId, UUID folderOwner) {

        FolderEntity foundFolder = folderRepository.findByFolderIdAndFolderOwner_UserId(folderId, folderOwner);

        if (foundFolder == null) {
            throw new FolderDoesntExistException();
        }

        return foundFolder;
    }

    /**
     * Skapar en folder baserat på informationen i request.
     *
     * @param request - innehåller namn på folder, ägarens användar-id samt id:et för parentFolder OM det ska finnas.
     * @return - FolderEntity-objektet OM det skapas.
     * @throws FolderNameIsEmptyException - OM foldernamn saknas eller är tomt.
     * @throws FolderOwnerIsEmptyException - OM ägare saknas eller inte existerar.
     * @throws FolderDoesntExistException - OM den angivna parent-foldern inte finns.
     */
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

    /**
     * Raderar en folder baserat på folderId och folderOwner
     *
     * @param folderId - id:et för foldern som ska raderas.
     * @param folderOwner - användar-id för ägaren av foldern.
     * @return - det raderade FolderEntity-objektet.
     * @throws FolderDoesntExistException - OM foldern inte existerar eller inte tillhör ägaren via findFolderById-funktionen.
     * @throws FolderOwnerIsntSameException - OM foldern inte tillhör den angivna ägaren
     */
    @Override
    public FolderEntity deleteFolder(UUID folderId, UUID folderOwner) {
        FolderEntity deleteFolder = findFolderById(folderId, folderOwner);

        if (!deleteFolder.getFolderOwner().getUserId().equals(folderOwner)) {
            throw new FolderOwnerIsntSameException();
        }

        folderRepository.delete(deleteFolder);

        return deleteFolder;
    }

    /**
     * Hämtar en lista med folders som en användare äger, OM de finns.
     *
     * @param ownerId - id:et för ägaren.
     * @return - en lista med FolderEntity-objekt om de finns.
     */
    @Override
    public List<FolderEntity> getAllFoldersByUser(UUID ownerId) {
        return folderRepository.findAllByFolderOwner_UserId(ownerId);
    }

    /**
     * Uppdaterar en folder via information från request. Endast namnet kan uppdateras.
     *
     * @param folderId - id:et för vilken folder som ska uppdateras.
     * @param request - den nya datan/informationen som ska uppdatera ett FileEntity-objekt i databasen.
     * @return - det uppdaterade FolderEntity-objektet med ett nytt namn.
     * @throws FolderDoesntExistException - OM FolderEntity-objektet inte existerar.
     */
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
