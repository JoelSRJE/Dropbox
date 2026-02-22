package project.dropbox.requests.file;

public record CreateFileRequest(
        String fileName,
        String contentType,
        byte[] data,
        String folder,
        String fileOwner
) {
}
