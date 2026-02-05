package project.dropbox.requests.folder;

import java.util.UUID;

public record CreateFolderRequest(
        String folderName,
        UUID ownerId,
        UUID parentFolder
) {
}
