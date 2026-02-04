package project.dropbox.exceptions.file;

public class FileOwnerIdIsNullException extends RuntimeException {
    public FileOwnerIdIsNullException() {
        super("FileOwner ID is null!");
    }
}
