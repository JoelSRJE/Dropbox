package project.dropbox.exceptions.file;

public class FileIdIsNullException extends RuntimeException {
    public FileIdIsNullException() {
        super("fileId is null!");
    }
}
