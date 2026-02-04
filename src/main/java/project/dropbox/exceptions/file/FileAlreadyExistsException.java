package project.dropbox.exceptions.file;

public class FileAlreadyExistsException extends RuntimeException {
    public FileAlreadyExistsException() {
        super("File already exists in database!");
    }
}
