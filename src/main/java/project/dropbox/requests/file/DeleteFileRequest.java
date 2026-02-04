package project.dropbox.requests.file;

import java.util.UUID;

public record DeleteFileRequest(
        UUID fileId,
        UUID ownerId
) {
}
