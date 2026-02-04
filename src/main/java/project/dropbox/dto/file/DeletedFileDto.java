package project.dropbox.dto.file;

import project.dropbox.dto.user.GetUserDto;
import project.dropbox.models.file.FileEntity;
import project.dropbox.models.folder.FolderEntity;

import java.util.UUID;

public record DeletedFileDto(
        UUID fileId,
        String fileName,
        byte[] data,
        FolderEntity folder,
        GetUserDto fileOwner
) {
    public static DeletedFileDto from(FileEntity file) {
        return new DeletedFileDto(
                file.getFileId(),
                file.getFileName(),
                file.getData(),
                file.getFolder(),
                GetUserDto.from(file.getFileOwner())
        );
    }
}
