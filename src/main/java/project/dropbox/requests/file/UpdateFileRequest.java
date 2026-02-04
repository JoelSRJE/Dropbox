package project.dropbox.requests.file;

import java.util.UUID;

public record UpdateFileRequest(
        String fileName,
        UUID ownerId
) {
}
