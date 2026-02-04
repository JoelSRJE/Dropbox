package project.dropbox.requests.file;

import project.dropbox.models.folder.FolderEntity;
import project.dropbox.models.user.User;

public record CreateFileRequest(
        String fileName,
        byte[] data,
        FolderEntity folder,
        User fileOwner
) {
}
