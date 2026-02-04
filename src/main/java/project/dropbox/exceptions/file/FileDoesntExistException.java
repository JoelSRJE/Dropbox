package project.dropbox.exceptions.file;

public class FileDoesntExistException extends RuntimeException {
    public FileDoesntExistException() {
        super("File doesn't exist in Database!");
    }
}
