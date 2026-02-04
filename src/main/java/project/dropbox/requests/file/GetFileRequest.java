package project.dropbox.requests.file;

import java.util.UUID;

public record GetFileRequest(
        UUID fileId,
        UUID fileOwner
) {
}
