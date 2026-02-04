package project.dropbox.exceptions.file;

public class FileNameIsEmptyException extends RuntimeException {
    public FileNameIsEmptyException() {
        super("FileName is empty!");
    }
}
