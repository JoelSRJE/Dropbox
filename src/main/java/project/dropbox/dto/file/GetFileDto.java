package project.dropbox.dto.file;

import project.dropbox.dto.folder.GetFolderDto;
import project.dropbox.dto.user.GetUserDto;
import project.dropbox.models.file.FileEntity;

import java.util.UUID;

public record GetFileDto(
        UUID fileId,
        String fileName,
        long size,
        UUID folderId,
        String folderName,
        GetUserDto fileOwner
) {
    public static GetFileDto from(FileEntity file) {
        return new GetFileDto(
                file.getFileId(),
                file.getFileName(),
                file.getData().length,
                file.getFolder().getFolderId(),
                file.getFolder().getFolderName(),
                GetUserDto.from(file.getFileOwner())
        );
    }
}
