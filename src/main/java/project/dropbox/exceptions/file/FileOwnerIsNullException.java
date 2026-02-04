package project.dropbox.exceptions.file;

public class FileOwnerIsNullException extends RuntimeException {
    public FileOwnerIsNullException() {
        super("FileOwner is null!");
    }
}
