package project.dropbox.dto.folder;


import project.dropbox.dto.user.GetUserDto;
import project.dropbox.models.folder.FolderEntity;

import java.util.UUID;

public record DeletedFolderDto(
        UUID folderId,
        String folderName,
        GetUserDto folderOwner,
        UUID parentFolder
) {
    public static DeletedFolderDto from(FolderEntity folder) {
        return new DeletedFolderDto(
                folder.getFolderId(),
                folder.getFolderName(),
                GetUserDto.from(folder.getFolderOwner()),
                folder.getParentFolder() != null
                ? folder.getParentFolder().getFolderId() : null
        );
    }
}
