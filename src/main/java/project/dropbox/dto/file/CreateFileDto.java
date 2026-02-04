package project.dropbox.dto.file;

import project.dropbox.dto.user.GetUserDto;
import project.dropbox.models.file.FileEntity;
import project.dropbox.models.folder.FolderEntity;

import java.util.UUID;

public record CreateFileDto(
        UUID fileId,
        String fileName,
        byte[] data,
        GetUserDto owner,
        FolderEntity folder
) {
    public static CreateFileDto from(FileEntity file) {
        return new CreateFileDto(
                file.getFileId(),
                file.getFileName(),
                file.getData(),
                GetUserDto.from(file.getFileOwner()),
                file.getFolder()
                );
    }
}
