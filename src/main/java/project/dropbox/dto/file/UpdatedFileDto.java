package project.dropbox.dto.file;

import project.dropbox.dto.user.GetUserDto;
import project.dropbox.models.file.FileEntity;
import project.dropbox.models.folder.FolderEntity;

import java.util.UUID;

public record UpdatedFileDto(
        UUID fileId,
        String fileName,
        byte[] data,
        GetUserDto owner,
        FolderEntity folder
) {
    public static UpdatedFileDto from(FileEntity file) {
        return new UpdatedFileDto(
                file.getFileId(),
                file.getFileName(),
                file.getData(),
                GetUserDto.from(file.getFileOwner()),
                file.getFolder()
        );
    }
}
