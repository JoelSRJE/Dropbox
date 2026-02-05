package project.dropbox.dto.folder;

import project.dropbox.models.folder.FolderEntity;

public record UpdateFolderDto(
        String folderName
) {
    public static UpdateFolderDto from(FolderEntity folder) {
        return new UpdateFolderDto(folder.getFolderName());
    }
}
