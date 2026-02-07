package project.dropbox.requests.file;

public record CreateFileRequest(
        String fileName,
        byte[] data,
        String folder,
        String fileOwner
) {
}
