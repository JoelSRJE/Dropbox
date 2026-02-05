package project.dropbox.exceptions.folder;

public class FolderNameIsEmptyException extends RuntimeException {
    public FolderNameIsEmptyException() {
        super("FolderName is empty!");
    }
}
