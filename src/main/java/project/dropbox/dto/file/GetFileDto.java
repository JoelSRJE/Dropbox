package project.dropbox.dto.file;

import project.dropbox.models.file.FileEntity;
import project.dropbox.models.folder.FolderEntity;
import project.dropbox.models.user.User;

import java.util.UUID;

public record GetFileDto(
        UUID fileId,
        String fileName,
        byte[] data,
        FolderEntity folder,
        User fileOwner
) {
    public static GetFileDto from(FileEntity file) {
        return new GetFileDto(
                file.getFileId(),
                file.getFileName(),
                file.getData(),
                file.getFolder(),
                file.getFileOwner()
        );
    }
}
