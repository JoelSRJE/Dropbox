package project.dropbox.exceptions.file;

public class FileDataIsNullException extends RuntimeException {
    public FileDataIsNullException() {
        super("FileData is null!");
    }
}
